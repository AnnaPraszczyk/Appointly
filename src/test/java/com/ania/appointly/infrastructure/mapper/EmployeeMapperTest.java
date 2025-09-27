package com.ania.appointly.infrastructure.mapper;
import com.ania.appointly.domain.exeptions.EmployeeValidationException;
import com.ania.appointly.domain.model.*;
import com.ania.appointly.infrastructure.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeMapperTest {
    private EmployeeMapper employeeMapper;
    @Mock
    private CompanyMapper companyMapper;
    @Mock
    private OfferedServiceMapper serviceMapper;
    @Mock
    private ScheduleSlotEmbeddableMapper scheduleSlotMapper;
    @Mock
    private ReservationMapper reservationMapper;
    @Mock
    private UserMapper userMapper;
    private MapperFacade mapperFacade;

    @BeforeEach
    void setUp() {
        employeeMapper = new EmployeeMapper();
        mapperFacade = new MapperFacade(
                companyMapper,
                serviceMapper,
                employeeMapper,
                reservationMapper,
                scheduleSlotMapper,
                userMapper);
    }

    @Test
    void mapEmployeeToEntity() {
        UUID companyId = UUID.randomUUID();
        UUID employeeId = UUID.randomUUID();
        UUID reservationId = UUID.randomUUID();
        User user = mock(User.class);
        OfferedService service = mock(OfferedService.class);
        Employee employeeRef = mock(Employee.class);
        Company company = Company.builder()
                .id(companyId)
                .name("Studio Lux")
                .description("Beauty & Wellness")
                .address("Main Street 1")
                .phone("123456789")
                .build();
        ScheduleSlot slot = ScheduleSlot.builder()
                .day(DayOfWeek.WEDNESDAY)
                .time(LocalTime.of(10, 0))
                .available(true)
                .build();
        Reservation reservation = Reservation.builder()
                .id(reservationId)
                .user(user)
                .service(service)
                .employee(employeeRef)
                .dateTime(Instant.parse("2025-10-01T10:00:00Z"))
                .price(BigDecimal.valueOf(150))
                .totalPrice(BigDecimal.valueOf(150))
                .build();
        Employee employee = Employee.builder()
                .id(employeeId)
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna.nowak@wp.pl")
                .phoneNumber("555-123-112")
                .company(company)
                .specialization("Hairdresser")
                .scheduleSlot(slot)
                .reservation(reservation)
                .build();
        CompanyEntity companyEntity = CompanyEntity.builder()
                .id(companyId)
                .name("Studio Lux")
                .description("Beauty & Wellness")
                .address("Main Street 1")
                .phone("123456789")
                .build();
        ScheduleSlotEmbeddable slotEmbeddable = new ScheduleSlotEmbeddable();
        ReservationEntity reservationEntity = ReservationEntity.builder()
                .id(reservationId)
                .user(UserEntity.builder().id(UUID.randomUUID()).build())
                .service(OfferedServiceEntity.builder().id(UUID.randomUUID()).build())
                .employee(EmployeeEntity.builder().id(employeeId).build())
                .dateTime(Instant.parse("2025-10-01T10:00:00Z"))
                .status(ReservationStatus.PENDING)
                .price(BigDecimal.valueOf(150))
                .totalPrice(BigDecimal.valueOf(150))
                .createdAt(Instant.now())
                .build();
        when(companyMapper.toEntity(company, mapperFacade)).thenReturn(companyEntity);
        when(scheduleSlotMapper.toEmbeddable(slot)).thenReturn(slotEmbeddable);
        when(reservationMapper.toEntity(reservation, mapperFacade)).thenReturn(reservationEntity);
        EmployeeEntity result = employeeMapper.toEntity(employee, mapperFacade);

        assertEquals(employeeId, result.getId());
        assertEquals("Anna", result.getFirstName());
        assertEquals("Nowak", result.getLastName());
        assertEquals("anna.nowak@wp.pl", result.getEmail());
        assertEquals("555-123-112", result.getPhoneNumber());
        assertEquals(companyEntity, result.getCompany());
        assertEquals(List.of("Hairdresser"), result.getSpecializations());
        assertEquals(List.of(slotEmbeddable), result.getSchedule());
        assertEquals(List.of(reservationEntity), result.getReservations());
    }

    @Test
    void mapEntityToEmployee() {
        UUID companyId = UUID.randomUUID();
        UUID employeeId = UUID.randomUUID();
        UUID reservationId = UUID.randomUUID();
        User user = mock(User.class);
        OfferedService service = mock(OfferedService.class);
        Employee employee = mock(Employee.class);
        CompanyEntity companyEntity = CompanyEntity.builder()
                .id(companyId)
                .name("Studio Glam")
                .description("Makeup & Hair")
                .address("Second Street 2")
                .phone("987654321")
                .build();
        ScheduleSlotEmbeddable slotEmbeddable = new ScheduleSlotEmbeddable();
        ReservationEntity reservationEntity = ReservationEntity.builder()
                .id(reservationId)
                .user(UserEntity.builder().id(UUID.randomUUID()).build())
                .service(OfferedServiceEntity.builder().id(UUID.randomUUID()).build())
                .employee(EmployeeEntity.builder().id(employeeId).build())
                .dateTime(Instant.parse("2025-10-01T10:00:00Z"))
                .status(ReservationStatus.PENDING)
                .price(BigDecimal.valueOf(150))
                .totalPrice(BigDecimal.valueOf(150))
                .createdAt(Instant.now())
                .build();
        EmployeeEntity entity = EmployeeEntity.builder()
                .id(employeeId)
                .firstName("Ewa")
                .lastName("Smith")
                .email("ewa.smith@wp.pl")
                .phoneNumber("555-000-111")
                .company(companyEntity)
                .specializations(List.of("Makeup"))
                .schedule(List.of(slotEmbeddable))
                .reservations(List.of(reservationEntity))
                .build();
        ScheduleSlot slot = ScheduleSlot.builder()
                .day(DayOfWeek.WEDNESDAY)
                .time(LocalTime.of(10, 0))
                .available(true)
                .build();
        Reservation reservation = Reservation.builder()
                .id(reservationId)
                .user(user)
                .service(service)
                .employee(employee)
                .dateTime(Instant.parse("2025-10-01T10:00:00Z"))
                .price(BigDecimal.valueOf(150))
                .totalPrice(BigDecimal.valueOf(150))
                .build();
        when(companyMapper.toDomain(companyEntity, mapperFacade)).thenReturn(
                Company.builder()
                        .id(companyId)
                        .name("Studio Glam")
                        .description("Makeup & Hair")
                        .address("Second Street 2")
                        .phone("987654321")
                        .build());
        when(scheduleSlotMapper.toDomain(slotEmbeddable)).thenReturn(slot);
        when(reservationMapper.toDomain(reservationEntity, mapperFacade)).thenReturn(reservation);
        Employee result = employeeMapper.toDomain(entity, mapperFacade);

        assertEquals(employeeId, result.getId());
        assertEquals("Ewa", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals("ewa.smith@wp.pl", result.getEmail());
        assertEquals("555-000-111", result.getPhoneNumber());
        assertEquals("Studio Glam", result.getCompany().getName());
        assertEquals(List.of("Makeup"), result.getSpecializations());
        assertEquals(List.of(slot), result.getSchedule());
        assertEquals(List.of(reservation), result.getReservations());
    }

    @Test
    void mapEmployeeWithEmptyListsToEntity() {
        UUID companyId = UUID.randomUUID();
        Company company = Company.builder()
                .id(companyId)
                .name("Studio Solo")
                .description("One-person business")
                .address("Quiet Street 5")
                .phone("111222333")
                .build();
        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jan.kowalski@wp.pl")
                .phoneNumber("555-000-999")
                .company(company)
                .build();
        CompanyEntity companyEntity = CompanyEntity.builder().id(companyId).build();
        when(companyMapper.toEntity(company, mapperFacade)).thenReturn(companyEntity);
        EmployeeEntity result = employeeMapper.toEntity(employee, mapperFacade);

        assertEquals(employee.getId(), result.getId());
        assertTrue(result.getSpecializations().isEmpty());
        assertTrue(result.getSchedule().isEmpty());
        assertTrue(result.getReservations().isEmpty());
    }

    @Test
    void mapEntityWithNullListsToEmployee() {
        UUID companyId = UUID.randomUUID();
        CompanyEntity companyEntity = CompanyEntity.builder().id(companyId).build();
        EmployeeEntity entity = EmployeeEntity.builder()
                .id(UUID.randomUUID())
                .firstName("Zofia")
                .lastName("Nowak")
                .email("zofia@studio.pl")
                .phoneNumber("555-111-222")
                .company(companyEntity)
                .specializations(null)
                .schedule(null)
                .reservations(null)
                .build();
        Company company = Company.builder()
                .id(companyId)
                .name("Studio Zofia")
                .description("Makeup & Style")
                .address("Style Street 7")
                .phone("999888777")
                .build();
        when(companyMapper.toDomain(companyEntity, mapperFacade)).thenReturn(company);
        Employee result = employeeMapper.toDomain(entity, mapperFacade);

        assertEquals(entity.getId(), result.getId());
        assertTrue(result.getSpecializations().isEmpty());
        assertTrue(result.getSchedule().isEmpty());
        assertTrue(result.getReservations().isEmpty());
    }

    @Test
    void mapEntityWithIncompleteScheduleAndReservation() {
        UUID companyId = UUID.randomUUID();
        CompanyEntity companyEntity = CompanyEntity.builder().id(companyId).build();
        ScheduleSlotEmbeddable incompleteSlot = new ScheduleSlotEmbeddable();
        ReservationEntity incompleteReservation = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .user(null)
                .service(null)
                .employee(null)
                .dateTime(Instant.parse("2025-10-01T10:00:00Z"))
                .status(ReservationStatus.PENDING)
                .price(BigDecimal.valueOf(100))
                .totalPrice(BigDecimal.valueOf(100))
                .createdAt(Instant.now())
                .build();
        EmployeeEntity entity = EmployeeEntity.builder()
                .id(UUID.randomUUID())
                .firstName("Tomasz")
                .lastName("Mazur")
                .email("t.mazur@studio.pl")
                .phoneNumber("555-333-444")
                .company(companyEntity)
                .specializations(List.of("Barber"))
                .schedule(List.of(incompleteSlot))
                .reservations(List.of(incompleteReservation))
                .build();
        Company company = Company.builder()
                .id(companyId)
                .name("Studio TM")
                .description("Barber & Style")
                .address("Sharp Street 9")
                .phone("123123123")
                .build();
        ScheduleSlot slot = ScheduleSlot.builder()
                .day(DayOfWeek.FRIDAY)
                .time(LocalTime.of(14, 0))
                .available(true)
                .build();
        Reservation reservation = Reservation.builder()
                .id(incompleteReservation.getId())
                .user(mock(User.class))
                .service(mock(OfferedService.class))
                .employee(mock(Employee.class))
                .dateTime(incompleteReservation.getDateTime())
                .price(incompleteReservation.getPrice())
                .totalPrice(incompleteReservation.getTotalPrice())
                .build();
        when(companyMapper.toDomain(companyEntity, mapperFacade)).thenReturn(company);
        when(scheduleSlotMapper.toDomain(incompleteSlot)).thenReturn(slot);
        when(reservationMapper.toDomain(incompleteReservation, mapperFacade)).thenReturn(reservation);
        Employee result = employeeMapper.toDomain(entity, mapperFacade);

        assertEquals(1, result.getSchedule().size());
        assertEquals(1, result.getReservations().size());
    }

    @Test
    void throwExceptionWhenEmployeeFirstNameIsBlank() {
        Company company = mock(Company.class);

        assertThrows(EmployeeValidationException.class, () -> Employee.builder()
                .id(UUID.randomUUID())
                .firstName(" ")
                .lastName("Nowak")
                .email("nowak@studio.pl")
                .phoneNumber("555-000-111")
                .company(company)
                .build());
    }
}