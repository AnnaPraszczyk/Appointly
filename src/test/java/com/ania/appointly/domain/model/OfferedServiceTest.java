package com.ania.appointly.domain.model;
import com.ania.appointly.domain.exeptions.ServiceValidationException;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class OfferedServiceTest {
    private final UUID validId = UUID.randomUUID();
    @Test
    void shouldCreateServiceWithValidData() {
        OfferedService service = OfferedService.builder()
                .id(validId)
                .name("Haircut")
                .description("Basic haircut service")
                .price(BigDecimal.valueOf(50))
                .duration(Duration.ofMinutes(30))
                .company(mockCompany())
                .availableEmployee(mockEmployee())
                .build();

        assertEquals("Haircut", service.getName());
        assertEquals("Basic haircut service", service.getDescription());
        assertEquals(BigDecimal.valueOf(50), service.getPrice());
        assertEquals(Duration.ofMinutes(30), service.getDuration());
        assertEquals(1, service.getAvailableEmployees().size());
    }

    @Test
    void throwExceptionWhenNameIsBlank() {
        assertThrows(ServiceValidationException.class, () ->
                OfferedService.builder()
                        .id(validId)
                        .name(" ")
                        .description("desc")
                        .price(BigDecimal.valueOf(50))
                        .duration(Duration.ofMinutes(30))
                        .company(mockCompany())
                        .build());
    }

    @Test
    void throwExceptionWhenDescriptionIsBlank() {
        assertThrows(ServiceValidationException.class, () ->
                OfferedService.builder()
                        .id(validId)
                        .name("Haircut")
                        .description(" ")
                        .price(BigDecimal.valueOf(50))
                        .duration(Duration.ofMinutes(30))
                        .company(mockCompany())
                        .build());
    }

    @Test
    void throwExceptionWhenIdIsNull() {
        assertThrows(ServiceValidationException.class, () ->
                OfferedService.builder()
                        .id(null)
                        .name("Haircut")
                        .description("desc")
                        .price(BigDecimal.valueOf(50))
                        .duration(Duration.ofMinutes(30))
                        .company(mockCompany())
                        .build());
    }

    @Test
    void throwExceptionWhenPriceIsNull() {
        assertThrows(ServiceValidationException.class, () ->
                OfferedService.builder()
                        .id(validId)
                        .name("Haircut")
                        .description("desc")
                        .price(null)
                        .duration(Duration.ofMinutes(30))
                        .company(mockCompany())
                        .build());
    }

    @Test
    void throwExceptionWhenDurationIsNull() {
        assertThrows(ServiceValidationException.class, () ->
                OfferedService.builder()
                        .id(validId)
                        .name("Haircut")
                        .description("desc")
                        .price(BigDecimal.valueOf(50))
                        .duration(null)
                        .company(mockCompany())
                        .build());
    }

    @Test
    void throwExceptionWhenCompanyIsNull() {
        assertThrows(ServiceValidationException.class, () ->
                OfferedService.builder()
                        .id(validId)
                        .name("Haircut")
                        .description("desc")
                        .price(BigDecimal.valueOf(50))
                        .duration(Duration.ofMinutes(30))
                        .company(null)
                        .build());
    }

    @Test
    void returnUnmodifiableAvailableEmployeesList() {
        OfferedService service = OfferedService.builder()
                .id(validId)
                .name("Haircut")
                .description("desc")
                .price(BigDecimal.valueOf(50))
                .duration(Duration.ofMinutes(30))
                .company(mockCompany())
                .build();

        assertNotNull(service.getAvailableEmployees());
        assertTrue(service.getAvailableEmployees().isEmpty());
        assertThrows(UnsupportedOperationException.class, () ->
                service.getAvailableEmployees().add(mockEmployee()));
    }

    @Test
    void createServiceWithMultipleAvailableEmployees() {
        OfferedService service = OfferedService.builder()
                .id(validId)
                .name("Haircut")
                .description("desc")
                .price(BigDecimal.valueOf(50))
                .duration(Duration.ofMinutes(30))
                .company(mockCompany())
                .availableEmployee(mockEmployee())
                .availableEmployee(mockEmployee())
                .build();

        assertEquals(2, service.getAvailableEmployees().size());
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

    private Employee mockEmployee() {
        return Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Anna")
                .lastName("Kowalska")
                .email("anna@example.com")
                .phoneNumber("555-123")
                .company(mockCompany())
                .build();
    }
}