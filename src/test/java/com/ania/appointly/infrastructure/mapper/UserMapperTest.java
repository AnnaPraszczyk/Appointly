package com.ania.appointly.infrastructure.mapper;
import com.ania.appointly.domain.model.Company;
import com.ania.appointly.domain.model.Role;
import com.ania.appointly.domain.model.User;
import com.ania.appointly.infrastructure.entity.CompanyEntity;
import com.ania.appointly.infrastructure.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {
    private UserMapper userMapper;
    @Mock
    private CompanyMapper companyMapper;
    private MapperFacade mapperFacade;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
        mapperFacade = new MapperFacade(
                companyMapper,
                null,
                null,
                null,
                null,
                userMapper
        );
    }

    @Test
    void mapUserToEntity() {
        UUID userId = UUID.randomUUID();
        UUID companyId = UUID.randomUUID();
        Company company = Company.builder()
                .id(companyId)
                .name("Studio Lux")
                .description("Beauty & Wellness")
                .address("Main Street 1")
                .phone("123456789")
                .build();
        User user = User.builder()
                .id(userId)
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna@studio.pl")
                .phoneNumber("555-123-456")
                .role(Role.PROVIDER)
                .company(company)
                .build();
        CompanyEntity companyEntity = CompanyEntity.builder().id(companyId).build();
        when(companyMapper.toEntity(company, mapperFacade)).thenReturn(companyEntity);
        UserEntity result = userMapper.toEntity(user, mapperFacade);

        assertEquals(userId, result.getId());
        assertEquals("Anna", result.getFirstName());
        assertEquals("Nowak", result.getLastName());
        assertEquals("anna@studio.pl", result.getEmail());
        assertEquals("555-123-456", result.getPhoneNumber());
        assertEquals(Role.PROVIDER, result.getRole());
        assertEquals(companyEntity, result.getCompany());
    }

    @Test
    void mapEntityToUser() {
        UUID userId = UUID.randomUUID();
        UserEntity entity = UserEntity.builder()
                .id(userId)
                .firstName("Ewa")
                .lastName("Nowak")
                .email("ewa@studio.pl")
                .phoneNumber("555-000-111")
                .role(Role.EMPLOYEE)
                .build();
        User result = userMapper.toDomain(entity, mapperFacade);

        assertEquals(userId, result.getId());
        assertEquals("Ewa", result.getFirstName());
        assertEquals("Nowak", result.getLastName());
        assertEquals("ewa@studio.pl", result.getEmail());
        assertEquals("555-000-111", result.getPhoneNumber());
        assertEquals(Role.EMPLOYEE, result.getRole());
    }

    @Test
    void mapUserWithoutCompanyToEntity() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jan@studio.pl")
                .phoneNumber("555-222-333")
                .role(Role.ADMIN)
                .build();
        UserEntity result = userMapper.toEntity(user, mapperFacade);

        assertNull(result.getCompany());
    }

    @Test
    void mapEntityWithoutCompanyToUser() {
        UserEntity entity = UserEntity.builder()
                .id(UUID.randomUUID())
                .firstName("Zofia")
                .lastName("Nowak")
                .email("zofia@studio.pl")
                .phoneNumber("555-444-555")
                .role(Role.CLIENT)
                .build();
        User result = userMapper.toDomain(entity, mapperFacade);

        assertNull(result.getCompany());
    }
}