package com.ania.appointly.infrastructure.mapper;
import com.ania.appointly.domain.exeptions.CompanyValidationException;
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
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyMapperTest {
    private CompanyMapper companyMapper;
    @Mock
    private OfferedServiceMapper serviceMapper;
    @Mock
    private EmployeeMapper employeeMapper;

    private MapperFacade mapperFacade;

    @BeforeEach
    void setUp() {
        companyMapper = new CompanyMapper();
        mapperFacade = new MapperFacade(
                companyMapper,
                serviceMapper,
                employeeMapper,
                null,
                null,
                null
        );
    }

    @Test
    void mapCompanyToEntity() {
        UUID companyId = UUID.randomUUID();
        Company company = Company.builder()
                .id(companyId)
                .name("Studio Lux")
                .description("Beauty & Wellness")
                .address("Main Street 1")
                .phone("123456789")
                .build();
        OfferedService service = OfferedService.builder()
                .id(UUID.randomUUID())
                .name("Massage")
                .description("Relaxing massage")
                .price(BigDecimal.valueOf(199))
                .duration(Duration.ofMinutes(60))
                .company(company)
                .build();
        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna.nowak@wp.pl")
                .phoneNumber("555-123-112")
                .company(company)
                .build();
        company = Company.builder()
                .id(companyId)
                .name("Studio Lux")
                .description("Beauty & Wellness")
                .address("Main Street 1")
                .phone("123456789")
                .service(service)
                .employee(employee)
                .build();
        OfferedServiceEntity serviceEntity = OfferedServiceEntity.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .price(service.getPrice())
                .duration(service.getDuration())
                .company(CompanyEntity.builder().id(companyId).build())
                .build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .company(CompanyEntity.builder().id(companyId).build())
                .build();
        when(serviceMapper.toEntity(service, mapperFacade)).thenReturn(serviceEntity);
        when(employeeMapper.toEntity(employee, mapperFacade)).thenReturn(employeeEntity);
        CompanyEntity result = companyMapper.toEntity(company, mapperFacade);

        assertEquals(companyId, result.getId());
        assertEquals("Studio Lux", result.getName());
        assertEquals(1, result.getServices().size());
        assertEquals(1, result.getEmployees().size());
    }

    @Test
    void mapEntityToCompany() {
        UUID companyId = UUID.randomUUID();
        CompanyEntity entity = CompanyEntity.builder()
                .id(companyId)
                .name("Studio Glam")
                .description("Makeup & Hair")
                .address("Second Street 2")
                .phone("987654321")
                .build();
        OfferedServiceEntity serviceEntity = OfferedServiceEntity.builder()
                .id(UUID.randomUUID())
                .name("Makeup")
                .description("Evening makeup")
                .price(BigDecimal.valueOf(149))
                .duration(Duration.ofMinutes(45))
                .company(entity)
                .build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .id(UUID.randomUUID())
                .firstName("Ewa")
                .lastName("Nowak")
                .email("ewa.nowak@wp.pl")
                .phoneNumber("555-000-111")
                .company(entity)
                .build();
        entity.getServices().add(serviceEntity);
        entity.getEmployees().add(employeeEntity);
        Company company = Company.builder()
                .id(companyId)
                .name(entity.getName())
                .description(entity.getDescription())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .build();
        OfferedService service = OfferedService.builder()
                .id(serviceEntity.getId())
                .name(serviceEntity.getName())
                .description(serviceEntity.getDescription())
                .price(serviceEntity.getPrice())
                .duration(serviceEntity.getDuration())
                .company(company)
                .build();
        Employee employee = Employee.builder()
                .id(employeeEntity.getId())
                .firstName(employeeEntity.getFirstName())
                .lastName(employeeEntity.getLastName())
                .email(employeeEntity.getEmail())
                .phoneNumber(employeeEntity.getPhoneNumber())
                .company(company)
                .build();
        when(serviceMapper.toDomain(serviceEntity, mapperFacade)).thenReturn(service);
        when(employeeMapper.toDomain(employeeEntity, mapperFacade)).thenReturn(employee);
        Company result = companyMapper.toDomain(entity, mapperFacade);

        assertEquals(companyId, result.getId());
        assertEquals("Studio Glam", result.getName());
        assertEquals(1, result.getServices().size());
        assertEquals(1, result.getEmployees().size());
    }

    @Test
    void mapCompanyWithEmptyListsToEntity() {
        UUID companyId = UUID.randomUUID();
        Company company = Company.builder()
                .id(companyId)
                .name("Empty Studio")
                .description("No services or staff")
                .address("Void Street 0")
                .phone("000000000")
                .build();

        CompanyEntity result = companyMapper.toEntity(company, mapperFacade);

        assertEquals(companyId, result.getId());
        assertEquals("Empty Studio", result.getName());
        assertTrue(result.getServices().isEmpty());
        assertTrue(result.getEmployees().isEmpty());
    }

    @Test
    void mapEntityWithNullListsToCompany() {
        UUID companyId = UUID.randomUUID();
        CompanyEntity entity = CompanyEntity.builder()
                .id(companyId)
                .name("Null Studio")
                .description("Null services and employees")
                .address("Null Street")
                .phone("111111111")
                .services(null)
                .employees(null)
                .build();

        Company result = companyMapper.toDomain(entity, mapperFacade);

        assertEquals(companyId, result.getId());
        assertEquals("Null Studio", result.getName());
        assertTrue(result.getServices().isEmpty());
        assertTrue(result.getEmployees().isEmpty());
    }

    @Test
    void mapEntityWithIncompleteServiceShouldThrowException() {
        UUID companyId = UUID.randomUUID();
        CompanyEntity entity = CompanyEntity.builder()
                .id(companyId)
                .name("Partial Studio")
                .description("Missing fields")
                .address("Halfway Street")
                .phone("222222222")
                .services(new ArrayList<>())
                .employees(new ArrayList<>())
                .build();
        OfferedServiceEntity incompleteService = OfferedServiceEntity.builder()
                .id(UUID.randomUUID())
                .name(null)
                .description(null)
                .price(null)
                .duration(null)
                .company(entity)
                .build();
        entity.getServices().add(incompleteService);
        when(serviceMapper.toDomain(incompleteService, mapperFacade))
                .thenThrow(new ServiceValidationException("Service name cannot be null or empty."));

        assertThrows(ServiceValidationException.class, () -> companyMapper.toDomain(entity, mapperFacade));
    }

    @Test
    void mapCompanyWithCyclicReferenceToEntity() {
        UUID companyId = UUID.randomUUID();
        Company company = Company.builder()
                .id(companyId)
                .name("Cyclic Studio")
                .description("Self-referencing service")
                .address("Loop Street")
                .phone("333333333")
                .build();
        OfferedService service = OfferedService.builder()
                .id(UUID.randomUUID())
                .name("Loop Service")
                .description("Refers back to company")
                .price(BigDecimal.valueOf(99))
                .duration(Duration.ofMinutes(30))
                .company(company)
                .build();
        company = Company.builder()
                .id(companyId)
                .name("Cyclic Studio")
                .description("Self-referencing service")
                .address("Loop Street")
                .phone("333333333")
                .service(service)
                .build();
        OfferedServiceEntity serviceEntity = OfferedServiceEntity.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .price(service.getPrice())
                .duration(service.getDuration())
                .company(CompanyEntity.builder().id(companyId).build())
                .build();
        when(serviceMapper.toEntity(service, mapperFacade)).thenReturn(serviceEntity);
        CompanyEntity result = companyMapper.toEntity(company, mapperFacade);

        assertEquals(companyId, result.getId());
        assertEquals(1, result.getServices().size());
    }

    @Test
    void throwExceptionWhenCompanyNameIsBlank() {
        UUID companyId = UUID.randomUUID();

        assertThrows(CompanyValidationException.class, () -> Company.builder()
                .id(companyId)
                .name("  ")
                .description("Valid description")
                .address("Valid address")
                .phone("123456789")
                .build());
    }

    @Test
    void throwExceptionWhenCompanyIdIsNull() {
        assertThrows(CompanyValidationException.class, () -> Company.builder()
                .id(null)
                .name("Valid name")
                .description("Valid description")
                .address("Valid address")
                .phone("123456789")
                .build());
    }
}