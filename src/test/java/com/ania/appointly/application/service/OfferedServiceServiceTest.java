package com.ania.appointly.application.service;
import com.ania.appointly.domain.exeptions.ServiceValidationException;
import com.ania.appointly.domain.model.Company;
import com.ania.appointly.domain.model.Employee;
import com.ania.appointly.domain.model.OfferedService;
import com.ania.appointly.infrastructure.inmemory.InMemoryServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.*;

class OfferedServiceServiceTest {
    private OfferedServiceService serviceService;
    private InMemoryServiceRepository serviceRepository;

    @BeforeEach
    void setUp() {
        serviceRepository = new InMemoryServiceRepository();
        serviceService = new OfferedServiceService(serviceRepository);
    }

    @Test
    void createServiceSuccessfully() {
        OfferedService service = createSampleService();
        OfferedService saved = serviceService.createService(service);

        assertEquals(service.getId(), saved.getId());
        assertTrue(serviceRepository.existsById(service.getId()));
    }

    @Test
    void throwExceptionWhenCreatingServiceWithExistingId() {
        OfferedService service = createSampleService();
        serviceRepository.save(service);

        assertThrows(ServiceValidationException.class, () -> serviceService.createService(service));
    }

    @Test
    void updateServiceSuccessfully() {
        OfferedService original = createSampleService();
        serviceRepository.save(original);
        OfferedService updated = OfferedService.builder()
                .id(original.getId())
                .name("Updated Name")
                .description(original.getDescription())
                .price(original.getPrice())
                .duration(original.getDuration())
                .company(original.getCompany())
                .availableEmployees(original.getAvailableEmployees())
                .build();
        OfferedService result = serviceService.updateService(updated);

        assertEquals("Updated Name", result.getName());
    }

    @Test
    void throwExceptionWhenUpdatingNonExistingService() {
        OfferedService service = createSampleService();

        assertThrows(ServiceValidationException.class, () -> serviceService.updateService(service));
    }

    @Test
    void deleteServiceSuccessfully() {
        OfferedService service = createSampleService();
        serviceRepository.save(service);

        serviceService.deleteService(service.getId());

        assertFalse(serviceRepository.existsById(service.getId()));
    }

    @Test
    void throwExceptionWhenDeletingNonExistingService() {
        UUID id = UUID.randomUUID();

        assertThrows(ServiceValidationException.class, () -> serviceService.deleteService(id));
    }

    @Test
    void returnServicesByCompanyId() {
        Company company = createSampleCompany();
        OfferedService service = createSampleServiceWithCompany(company);
        serviceRepository.save(service);
        List<OfferedService> result = serviceService.getServicesByCompany(company.getId());

        assertEquals(1, result.size());
    }

    @Test
    void returnServicesByEmployeeId() {
        Company company = createSampleCompany();
        Employee employee = createSampleEmployee(company);
        OfferedService service = createSampleServiceWithEmployee(company, employee);
        serviceRepository.save(service);
        List<OfferedService> result = serviceService.getServicesByEmployee(employee.getId());

        assertEquals(1, result.size());
    }

    @Test
    void throwExceptionWhenCreatingServiceWithNegativeDuration() {
        Company company = createSampleCompany();

        assertThrows(ServiceValidationException.class, () -> OfferedService.builder()
                .id(UUID.randomUUID())
                .name("Bad Service")
                .description("Invalid duration")
                .price(BigDecimal.valueOf(100))
                .duration(Duration.ofMinutes(-30))
                .company(company)
                .build());
    }

    @Test
    void createServiceWithoutEmployees() {
        Company company = createSampleCompany();
        OfferedService service = OfferedService.builder()
                .id(UUID.randomUUID())
                .name("Self-service")
                .description("No employee required")
                .price(BigDecimal.valueOf(50))
                .duration(Duration.ofMinutes(15))
                .company(company)
                .build();
        OfferedService saved = serviceService.createService(service);

        assertTrue(saved.getAvailableEmployees().isEmpty());
    }

    @Test
    void createServiceWithManyEmployees() {
        Company company = createSampleCompany();
        List<Employee> employees = IntStream.range(0, 1000)
                .mapToObj(i -> createSampleEmployee(company))
                .collect(Collectors.toList());
        OfferedService service = OfferedService.builder()
                .id(UUID.randomUUID())
                .name("Mega Service")
                .description("Handled by many")
                .price(BigDecimal.valueOf(500))
                .duration(Duration.ofMinutes(90))
                .company(company)
                .availableEmployees(employees)
                .build();
        OfferedService saved = serviceService.createService(service);

        assertEquals(1000, saved.getAvailableEmployees().size());
    }

    @Test
    void createServiceWithLongNameAndDescription() {
        Company company = createSampleCompany();
        Employee employee = createSampleEmployee(company);
        String longText = "x".repeat(1000);
        OfferedService service = OfferedService.builder()
                .id(UUID.randomUUID())
                .name(longText)
                .description(longText)
                .price(BigDecimal.valueOf(100))
                .duration(Duration.ofMinutes(60))
                .company(company)
                .availableEmployee(employee)
                .build();
        OfferedService saved = serviceService.createService(service);

        assertEquals(longText, saved.getName());
        assertEquals(longText, saved.getDescription());
    }

    private Company createSampleCompany() {
        return Company.builder()
                .id(UUID.randomUUID())
                .name("Test Company")
                .description("Test Description")
                .address("Test Address")
                .phone("555-000-111")
                .build();
    }

    private Employee createSampleEmployee(Company company) {
        return Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Ewa")
                .lastName("Nowak")
                .email("ewa@example.com")
                .phoneNumber("987654321")
                .company(company)
                .build();
    }

    private OfferedService createSampleService() {
        Company company = createSampleCompany();
        Employee employee = createSampleEmployee(company);

        return OfferedService.builder()
                .id(UUID.randomUUID())
                .name("Massage Therapy")
                .description("Relaxing full-body massage session")
                .price(BigDecimal.valueOf(150))
                .duration(Duration.ofMinutes(60))
                .company(company)
                .availableEmployee(employee)
                .build();
    }

    private OfferedService createSampleServiceWithCompany(Company company) {
        return OfferedService.builder()
                .id(UUID.randomUUID())
                .name("Consultation")
                .description("Initial health consultation")
                .price(BigDecimal.valueOf(50))
                .duration(Duration.ofMinutes(30))
                .company(company)
                .build();
    }

    private OfferedService createSampleServiceWithEmployee(Company company, Employee employee) {
        return OfferedService.builder()
                .id(UUID.randomUUID())
                .name("Therapy")
                .description("Therapeutic session")
                .price(BigDecimal.valueOf(120))
                .duration(Duration.ofMinutes(45))
                .company(company)
                .availableEmployee(employee)
                .build();
    }
}