package com.ania.appointly.domain.model;
import com.ania.appointly.domain.exeptions.UserValidationException;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private final UUID validId = UUID.randomUUID();

    @Test
    void createClientWithoutCompany() {
        User user = User.builder()
                .id(validId)
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna@example.com")
                .phoneNumber("555-123")
                .role(Role.CLIENT)
                .build();

        assertEquals("Anna", user.getFirstName());
        assertEquals(Role.CLIENT, user.getRole());
        assertNull(user.getCompany());
    }

    @Test
    void createProviderWithCompany() {
        Company company = mockCompany();
        User user = User.builder()
                .id(validId)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jan@example.com")
                .phoneNumber("555-456")
                .role(Role.PROVIDER)
                .company(company)
                .build();

        assertEquals("Jan", user.getFirstName());
        assertEquals(Role.PROVIDER, user.getRole());
        assertEquals(company, user.getCompany());
    }

    @Test
    void throwExceptionWhenProviderHasNoCompany() {
        assertThrows(UserValidationException.class, () ->
                User.builder()
                        .id(validId)
                        .firstName("Jan")
                        .lastName("Kowalski")
                        .email("jan@example.com")
                        .phoneNumber("555-456")
                        .role(Role.PROVIDER)
                        .company(null)
                        .build());
    }

    @Test
    void throwExceptionWhenNonProviderHasCompany() {
        assertThrows(UserValidationException.class, () ->
                User.builder()
                        .id(validId)
                        .firstName("Anna")
                        .lastName("Nowak")
                        .email("anna@example.com")
                        .phoneNumber("555-123")
                        .role(Role.CLIENT)
                        .company(mockCompany())
                        .build());
    }

    @Test
    void throwExceptionWhenFirstNameIsBlank() {
        assertThrows(UserValidationException.class, () ->
                User.builder()
                        .id(validId)
                        .firstName(" ")
                        .lastName("Nowak")
                        .email("anna@example.com")
                        .phoneNumber("555-123")
                        .role(Role.CLIENT)
                        .build());
    }

    @Test
    void throwExceptionWhenLastNameIsBlank() {
        assertThrows(UserValidationException.class, () ->
                User.builder()
                        .id(validId)
                        .firstName("Anna")
                        .lastName(" ")
                        .email("anna@example.com")
                        .phoneNumber("555-123")
                        .role(Role.CLIENT)
                        .build());
    }

    @Test
    void throwExceptionWhenEmailIsBlank() {
        assertThrows(UserValidationException.class, () ->
                User.builder()
                        .id(validId)
                        .firstName("Anna")
                        .lastName("Nowak")
                        .email(" ")
                        .phoneNumber("555-123")
                        .role(Role.CLIENT)
                        .build());
    }

    @Test
    void throwExceptionWhenPhoneNumberIsBlank() {
        assertThrows(UserValidationException.class, () ->
                User.builder()
                        .id(validId)
                        .firstName("Anna")
                        .lastName("Nowak")
                        .email("anna@example.com")
                        .phoneNumber(" ")
                        .role(Role.CLIENT)
                        .build());
    }

    @Test
    void throwExceptionWhenIdIsNull() {
        assertThrows(NullPointerException.class, () ->
                User.builder()
                        .id(null)
                        .firstName("Anna")
                        .lastName("Nowak")
                        .email("anna@example.com")
                        .phoneNumber("555-123")
                        .role(Role.CLIENT)
                        .build());
    }

    @Test
    void throwExceptionWhenRoleIsNull() {
        assertThrows(NullPointerException.class, () ->
                User.builder()
                        .id(validId)
                        .firstName("Anna")
                        .lastName("Nowak")
                        .email("anna@example.com")
                        .phoneNumber("555-123")
                        .role(null)
                        .build());
    }

    private Company mockCompany() {
        return Company.builder()
                .id(UUID.randomUUID())
                .name("MockCorp")
                .description("Mock company")
                .address("Mock Street 1")
                .phone("000-000-000")
                .build();
    }
}