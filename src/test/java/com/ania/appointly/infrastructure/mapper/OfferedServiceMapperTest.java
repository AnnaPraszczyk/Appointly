package com.ania.appointly.infrastructure.mapper;
import com.ania.appointly.domain.exeptions.ServiceValidationException;
import com.ania.appointly.domain.model.Company;
import com.ania.appointly.domain.model.Employee;
import com.ania.appointly.domain.model.OfferedService;
import com.ania.appointly.infrastructure.entity.CompanyEntity;
import com.ania.appointly.infrastructure.entity.EmployeeEntity;
import com.ania.appointly.infrastructure.entity.OfferedServiceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.mock;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferedServiceMapperTest {
    private OfferedServiceMapper offeredServiceMapper;
    @Mock
    private CompanyMapper companyMapper;
    @Mock
    private EmployeeMapper employeeMapper;
    private MapperFacade mapperFacade;

    @BeforeEach
    void setUp() {
        offeredServiceMapper = new OfferedServiceMapper();
        mapperFacade = new MapperFacade(
                companyMapper,
                offeredServiceMapper,
                employeeMapper,
                null,
                null,
                null);
    }

    @Test
    void mapOfferedServiceToEntity() {
        UUID companyId = UUID.randomUUID();
        UUID serviceId = UUID.randomUUID();
        UUID employeeId = UUID.randomUUID();
        Company company = Company.builder()
                .id(companyId)
                .name("Studio Lux")
                .description("Beauty & Wellness")
                .address("Main Street 1")
                .phone("123456789")
                .build();
        Employee employee = Employee.builder()
                .id(employeeId)
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna@studio.pl")
                .phoneNumber("555-123-456")
                .company(company)
                .build();
        OfferedService service = OfferedService.builder()
                .id(serviceId)
                .name("Massage")
                .description("Relaxing massage")
                .price(BigDecimal.valueOf(199))
                .duration(Duration.ofMinutes(60))
                .company(company)
                .availableEmployee(employee)
                .build();
        CompanyEntity companyEntity = CompanyEntity.builder().id(companyId).build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(employeeId).build();
        when(companyMapper.toEntity(company, mapperFacade)).thenReturn(companyEntity);
        when(employeeMapper.toEntity(employee, mapperFacade)).thenReturn(employeeEntity);
        OfferedServiceEntity result = offeredServiceMapper.toEntity(service, mapperFacade);

        assertEquals(serviceId, result.getId());
        assertEquals("Massage", result.getName());
        assertEquals("Relaxing massage", result.getDescription());
        assertEquals(BigDecimal.valueOf(199), result.getPrice());
        assertEquals(Duration.ofMinutes(60), result.getDuration());
        assertEquals(companyEntity, result.getCompany());
        assertEquals(List.of(employeeEntity), result.getAvailableEmployees());
    }

    @Test
    void mapEntityToOfferedService() {
        UUID companyId = UUID.randomUUID();
        UUID serviceId = UUID.randomUUID();
        UUID employeeId = UUID.randomUUID();
        CompanyEntity companyEntity = CompanyEntity.builder().id(companyId).build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(employeeId).build();
        OfferedServiceEntity entity = OfferedServiceEntity.builder()
                .id(serviceId)
                .name("Makeup")
                .description("Evening makeup")
                .price(BigDecimal.valueOf(149))
                .duration(Duration.ofMinutes(45))
                .company(companyEntity)
                .availableEmployees(List.of(employeeEntity))
                .build();
        Company company = Company.builder()
                .id(companyId)
                .name("Studio Glam")
                .description("Makeup & Hair")
                .address("Second Street 2")
                .phone("987654321")
                .build();
        Employee employee = Employee.builder()
                .id(employeeId)
                .firstName("Ewa")
                .lastName("Kowalska")
                .email("ewa@studio.pl")
                .phoneNumber("555-000-111")
                .company(company)
                .build();
        when(companyMapper.toDomain(companyEntity, mapperFacade)).thenReturn(company);
        when(employeeMapper.toDomain(employeeEntity, mapperFacade)).thenReturn(employee);
        OfferedService result = offeredServiceMapper.toDomain(entity, mapperFacade);

        assertEquals(serviceId, result.getId());
        assertEquals("Makeup", result.getName());
        assertEquals("Evening makeup", result.getDescription());
        assertEquals(BigDecimal.valueOf(149), result.getPrice());
        assertEquals(Duration.ofMinutes(45), result.getDuration());
        assertEquals(company, result.getCompany());
        assertEquals(List.of(employee), result.getAvailableEmployees());
    }

    @Test
    void mapServiceWithEmptyEmployeeList() {
        UUID serviceId = UUID.randomUUID();
        Company company = mock(Company.class);
        CompanyEntity companyEntity = mock(CompanyEntity.class);
        OfferedService service = OfferedService.builder()
                .id(serviceId)
                .name("Consultation")
                .description("Initial consultation")
                .price(BigDecimal.valueOf(99))
                .duration(Duration.ofMinutes(30))
                .company(company)
                .build();
        when(companyMapper.toEntity(company, mapperFacade)).thenReturn(companyEntity);
        OfferedServiceEntity result = offeredServiceMapper.toEntity(service, mapperFacade);

        assertTrue(result.getAvailableEmployees().isEmpty());
    }

    @Test
    void mapEntityWithNullEmployeeListToDomain() {
        UUID serviceId = UUID.randomUUID();
        CompanyEntity companyEntity = mock(CompanyEntity.class);
        Company company = mock(Company.class);
        OfferedServiceEntity entity = OfferedServiceEntity.builder()
                .id(serviceId)
                .name("Consultation")
                .description("Initial consultation")
                .price(BigDecimal.valueOf(99))
                .duration(Duration.ofMinutes(30))
                .company(companyEntity)
                .availableEmployees(null)
                .build();
        when(companyMapper.toDomain(companyEntity, mapperFacade)).thenReturn(company);
        OfferedService result = offeredServiceMapper.toDomain(entity, mapperFacade);

        assertTrue(result.getAvailableEmployees().isEmpty());
    }

    @Test
    void mapServiceWithEmptyEmployeeListToEntity() {
        UUID serviceId = UUID.randomUUID();
        Company company = mock(Company.class);
        CompanyEntity companyEntity = mock(CompanyEntity.class);
        OfferedService service = OfferedService.builder()
                .id(serviceId)
                .name("Consultation")
                .description("Initial consultation")
                .price(BigDecimal.valueOf(99))
                .duration(Duration.ofMinutes(30))
                .company(company)
                .build(); // brak availableEmployees
        when(companyMapper.toEntity(company, mapperFacade)).thenReturn(companyEntity);
        OfferedServiceEntity result = offeredServiceMapper.toEntity(service, mapperFacade);

        assertEquals(serviceId, result.getId());
        assertTrue(result.getAvailableEmployees().isEmpty());
    }

    @Test
    void throwExceptionWhenServiceNameIsBlank() {
        Company company = mock(Company.class);

        assertThrows(ServiceValidationException.class, () -> OfferedService.builder()
                .id(UUID.randomUUID())
                .name(" ")
                .description("Valid description")
                .price(BigDecimal.valueOf(100))
                .duration(Duration.ofMinutes(30))
                .company(company)
                .build());
    }
}