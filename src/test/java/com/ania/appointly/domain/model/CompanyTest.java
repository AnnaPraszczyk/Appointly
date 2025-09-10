package com.ania.appointly.domain.model;
import com.ania.appointly.domain.exeptions.CompanyValidationException;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {
    private final UUID validId = UUID.randomUUID();

    @Test
    void createCompanyWithValidData() {
        Company company = Company.builder()
                .id(validId)
                .name("Test Company")
                .description("Software development")
                .address("Main Street 123")
                .phone("123-456-789")
                .build();

        assertEquals("Test Company", company.getName());
        assertEquals("Software development", company.getDescription());
        assertEquals("Main Street 123", company.getAddress());
        assertEquals("123-456-789", company.getPhone());
        assertTrue(company.getServices().isEmpty());
        assertTrue(company.getEmployees().isEmpty());
    }

    @Test
    void throwExceptionWhenNameIsBlank() {
        Exception exception = assertThrows(CompanyValidationException.class, () ->
                Company.builder()
                        .id(validId)
                        .name(" ")
                        .description("desc")
                        .address("addr")
                        .phone("123")
                        .build()
        );
        assertEquals("Company name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void throwExceptionWhenDescriptionIsBlank() {
        Exception exception = assertThrows(CompanyValidationException.class, () ->
                Company.builder()
                        .id(validId)
                        .name("Name")
                        .description(" ")
                        .address("addr")
                        .phone("123")
                        .build()
        );
        assertEquals("Company description cannot be null or empty.", exception.getMessage());
    }

    @Test
    void throwExceptionWhenAddressIsBlank() {
        Exception exception = assertThrows(CompanyValidationException.class, () ->
                Company.builder()
                        .id(validId)
                        .name("Name")
                        .description("desc")
                        .address(" ")
                        .phone("123")
                        .build()
        );
        assertEquals("Company address cannot be null or empty.", exception.getMessage());
    }
    @Test
    void throwExceptionWhenPhoneIsBlank() {
        Exception exception = assertThrows(CompanyValidationException.class, () ->
                Company.builder()
                        .id(validId)
                        .name("Name")
                        .description("desc")
                        .address("addr")
                        .phone(" ")
                        .build()
        );
        assertEquals("Company phone cannot be null or empty.", exception.getMessage());
    }
    @Test
    void copyServicesAndEmployeesSafely() {
        Service service = Service.builder()
                .id(UUID.randomUUID())
                .name("Consulting")
                .description("IT consulting")
                .price(BigDecimal.valueOf(100))
                .duration(Duration.ofHours(1))
                .company(mockCompany())
                .build();

        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna@example.com")
                .phoneNumber("555-123")
                .company(mockCompany())
                .build();

        Company company = Company.builder()
                .id(validId)
                .name("Firm")
                .description("desc")
                .address("addr")
                .phone("123")
                .service(service)
                .employee(employee)
                .build();

        assertEquals(1, company.getServices().size());
        assertEquals(1, company.getEmployees().size());
    }

    @Test
    void returnUnmodifiableLists() {
        Company company = Company.builder()
                .id(UUID.randomUUID())
                .name("Firm")
                .description("desc")
                .address("addr")
                .phone("123")
                .build();

        assertThrows(UnsupportedOperationException.class, () -> company.getServices().add(mockService()));
        assertThrows(UnsupportedOperationException.class, () -> company.getEmployees().add(mockEmployee()));
    }

    @Test
    void handleNullListsGracefully() {
        Company company = Company.builder()
                .id(UUID.randomUUID())
                .name("Firm")
                .description("desc")
                .address("addr")
                .phone("123")
                .build();

        assertNotNull(company.getServices());
        assertNotNull(company.getEmployees());
        assertTrue(company.getServices().isEmpty());
        assertTrue(company.getEmployees().isEmpty());
    }

    @Test
    void compareCompaniesById() {
        UUID id = UUID.randomUUID();
        Company c1 = Company.builder()
                .id(id)
                .name("A")
                .description("desc")
                .address("addr")
                .phone("123")
                .build();
        Company c2 = Company.builder()
                .id(id)
                .name("B")
                .description("other")
                .address("other")
                .phone("456")
                .build();

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void buildCompanyWithMultipleEmployeesAndServices() {
        Company company = Company.builder()
                .id(UUID.randomUUID())
                .name("Firm")
                .description("desc")
                .address("addr")
                .phone("123")
                .service(mockService())
                .service(mockService())
                .employee(mockEmployee())
                .employee(mockEmployee())
                .build();

        assertEquals(2, company.getServices().size());
        assertEquals(2, company.getEmployees().size());
    }

    private Company mockCompany() {
        return Company.builder()
                .id(UUID.randomUUID())
                .name("Mock")
                .description("Mock Desc")
                .address("Mock Addr")
                .phone("000")
                .build();
    }

    private Service mockService() {
        return Service.builder()
                .id(UUID.randomUUID())
                .name("Consulting")
                .description("IT consulting service")
                .price(BigDecimal.valueOf(150))
                .duration(Duration.ofMinutes(60))
                .company(mockCompany())
                .build();
    }

    private Employee mockEmployee() {
        return Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna@example.com")
                .phoneNumber("555-123")
                .company(mockCompany())
                .build();
    }
}