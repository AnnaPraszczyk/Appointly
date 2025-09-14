package com.ania.appointly.application.service;
import com.ania.appointly.domain.exeptions.UserValidationException;
import com.ania.appointly.domain.model.Company;
import com.ania.appointly.domain.model.Role;
import com.ania.appointly.domain.model.User;
import com.ania.appointly.infrastructure.inmemory.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;
    private InMemoryUserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
        userService = new UserService(userRepository);
    }
    @Test
    void createUserSuccessfully() {
        User user = createSampleUser();
        User saved = userService.createUser(user);

        assertEquals(user.getId(), saved.getId());
        assertTrue(userRepository.existsById(user.getId()));
    }

    @Test
    void throwExceptionWhenCreatingUserWithExistingId() {
        User user = createSampleUser();
        userRepository.save(user);

        assertThrows(UserValidationException.class, () -> userService.createUser(user));
    }

    @Test
    void throwExceptionWhenCreatingUserWithNullId() {
        assertThrows(UserValidationException.class, () -> {
            User.builder()
                    .id(null)
                    .firstName("Anna")
                    .lastName("Nowak")
                    .email("anna@example.com")
                    .phoneNumber("123456789")
                    .role(Role.PROVIDER)
                    .company(createValidCompany())
                    .build();});
    }

    @Test
    void throwExceptionWhenCreatingUserWithEmptyFirstName() {
        assertThrows(UserValidationException.class, () -> {
            User.builder()
                    .id(UUID.randomUUID())
                    .firstName(" ")
                    .lastName("Nowak")
                    .email("anna@example.com")
                    .phoneNumber("123456789")
                    .role(Role.PROVIDER)
                    .company(createValidCompany())
                    .build();});
    }

    @Test
    void throwExceptionWhenProviderHasNoCompany() {
        assertThrows(UserValidationException.class, () -> {
            User.builder()
                    .id(UUID.randomUUID())
                    .firstName("Anna")
                    .lastName("Nowak")
                    .email("anna@example.com")
                    .phoneNumber("123456789")
                    .role(Role.PROVIDER)
                    .company(null)
                    .build();});
    }

    @Test
    void throwExceptionWhenNonProviderHasCompany() {
        assertThrows(UserValidationException.class, () -> {
            User.builder()
                    .id(UUID.randomUUID())
                    .firstName("Anna")
                    .lastName("Nowak")
                    .email("anna@example.com")
                    .phoneNumber("123456789")
                    .role(Role.CLIENT)
                    .company(createValidCompany())
                    .build();});
    }

    @Test
    void returnEmptyListWhenNoUsersMatchCompanyId() {
        UUID unknownCompanyId = UUID.randomUUID();
        List<User> users = userService.getUsersByCompany(unknownCompanyId);

        assertTrue(users.isEmpty());
    }

    @Test
    void returnEmptyListWhenNoUsersMatchRole() {
        List<User> admins = userService.getUsersByRole(Role.ADMIN);

        assertTrue(admins.isEmpty());
    }

    @Test
    void returnEmptyOptionalWhenUserNotFoundByEmail() {
        Optional<User> result = userService.getUserByEmail("nonexistent@example.com");

        assertTrue(result.isEmpty());
    }

    @Test
    void returnEmptyOptionalWhenUserNotFoundById() {
        Optional<User> result = userService.getUserById(UUID.randomUUID());
        assertTrue(result.isEmpty());
    }

    @Test
    void updateExistingUser() {
        User user = createSampleUser();
        userRepository.save(user);
        User updated = User.builder()
                .id(user.getId())
                .firstName("Updated")
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .company(user.getCompany())
                .build();
        User result = userService.updateUser(updated);

        assertEquals("Updated", result.getFirstName());
    }

    @Test
    void throwExceptionWhenUpdatingNonExistingUser() {
        User user = createSampleUser();

        assertThrows(UserValidationException.class, () -> userService.updateUser(user));
    }

    @Test
    void deleteUserSuccessfully() {
        User user = createSampleUser();
        userRepository.save(user);
        userService.deleteUser(user.getId());

        assertFalse(userRepository.existsById(user.getId()));
    }

    @Test
    void throwExceptionWhenDeletingNonExistingUser() {
        UUID id = UUID.randomUUID();

        assertThrows(UserValidationException.class, () -> userService.deleteUser(id));
    }

    @Test
    void findUserByEmail() {
        User user = createSampleUser();
        userRepository.save(user);
        Optional<User> found = userService.getUserByEmail(user.getEmail());

        assertTrue(found.isPresent());
        assertEquals(user.getEmail(), found.get().getEmail());
    }

    @Test
    void findUsersByRole() {
        User user = createSampleUser();
        userRepository.save(user);
        List<User> providers = userService.getUsersByRole(Role.PROVIDER);

        assertEquals(1, providers.size());
    }

    private User createSampleUser() {
        Company company = Company.builder()
                .id(UUID.randomUUID())
                .name("Test Company")
                .description("A company for testing purposes")
                .address("123 Test Street, Test")
                .phone("555-123-456")
                .build();

        return User.builder()
                .id(UUID.randomUUID())
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna@example.com")
                .phoneNumber("123456789")
                .role(Role.PROVIDER)
                .company(company)
                .build();
    }

    private Company createValidCompany() {
        return Company.builder()
                .id(UUID.randomUUID())
                .name("Test Company")
                .description("Test Description")
                .address("Test Address")
                .phone("555-000-111")
                .build();
    }
}