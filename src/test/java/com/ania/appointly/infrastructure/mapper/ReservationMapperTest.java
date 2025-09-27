package com.ania.appointly.infrastructure.mapper;
import com.ania.appointly.domain.model.*;
import com.ania.appointly.infrastructure.entity.EmployeeEntity;
import com.ania.appointly.infrastructure.entity.OfferedServiceEntity;
import com.ania.appointly.infrastructure.entity.ReservationEntity;
import com.ania.appointly.infrastructure.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationMapperTest {
    private ReservationMapper reservationMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private OfferedServiceMapper serviceMapper;
    @Mock
    private EmployeeMapper employeeMapper;
    private MapperFacade mapperFacade;

    @BeforeEach
    void setUp() {
        reservationMapper = new ReservationMapper();
        mapperFacade = new MapperFacade(
                null,
                serviceMapper,
                employeeMapper,
                reservationMapper,
                null,
                userMapper);
    }

    @Test
    void mapReservationToEntity() {
        UUID reservationId = UUID.randomUUID();
        Instant dateTime = Instant.parse("2025-10-01T10:00:00Z");
        User user = mock(User.class);
        OfferedService service = mock(OfferedService.class);
        Employee employee = mock(Employee.class);
        UserEntity userEntity = mock(UserEntity.class);
        OfferedServiceEntity serviceEntity = mock(OfferedServiceEntity.class);
        EmployeeEntity employeeEntity = mock(EmployeeEntity.class);
        Reservation reservation = Reservation.builder()
                .id(reservationId)
                .user(user)
                .service(service)
                .employee(employee)
                .dateTime(dateTime)
                .price(BigDecimal.valueOf(150))
                .totalPrice(BigDecimal.valueOf(150))
                .build();
        when(userMapper.toEntity(user, mapperFacade)).thenReturn(userEntity);
        when(serviceMapper.toEntity(service, mapperFacade)).thenReturn(serviceEntity);
        when(employeeMapper.toEntity(employee, mapperFacade)).thenReturn(employeeEntity);
        ReservationEntity result = reservationMapper.toEntity(reservation, mapperFacade);

        assertEquals(reservationId, result.getId());
        assertEquals(userEntity, result.getUser());
        assertEquals(serviceEntity, result.getService());
        assertEquals(employeeEntity, result.getEmployee());
        assertEquals(dateTime, result.getDateTime());
        assertEquals(ReservationStatus.PENDING, result.getStatus());
        assertEquals(BigDecimal.valueOf(150), result.getPrice());
        assertEquals(BigDecimal.valueOf(150), result.getTotalPrice());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void mapEntityToReservation() {
        UUID reservationId = UUID.randomUUID();
        Instant dateTime = Instant.parse("2025-10-01T10:00:00Z");
        Instant createdAt = Instant.now();
        UserEntity userEntity = mock(UserEntity.class);
        OfferedServiceEntity serviceEntity = mock(OfferedServiceEntity.class);
        EmployeeEntity employeeEntity = mock(EmployeeEntity.class);
        User user = mock(User.class);
        OfferedService service = mock(OfferedService.class);
        Employee employee = mock(Employee.class);
        ReservationEntity entity = ReservationEntity.builder()
                .id(reservationId)
                .user(userEntity)
                .service(serviceEntity)
                .employee(employeeEntity)
                .dateTime(dateTime)
                .status(ReservationStatus.CONFIRMED)
                .price(BigDecimal.valueOf(200))
                .totalPrice(BigDecimal.valueOf(220))
                .createdAt(createdAt)
                .build();
        when(userMapper.toDomain(userEntity, mapperFacade)).thenReturn(user);
        when(serviceMapper.toDomain(serviceEntity, mapperFacade)).thenReturn(service);
        when(employeeMapper.toDomain(employeeEntity, mapperFacade)).thenReturn(employee);
        Reservation result = reservationMapper.toDomain(entity, mapperFacade);

        assertEquals(reservationId, result.getId());
        assertEquals(user, result.getUser());
        assertEquals(service, result.getService());
        assertEquals(employee, result.getEmployee());
        assertEquals(dateTime, result.getDateTime());
        assertEquals(BigDecimal.valueOf(200), result.getPrice());
        assertEquals(BigDecimal.valueOf(220), result.getTotalPrice());
        assertNotNull(result.getCreatedAt());
        assertEquals(createdAt, result.getCreatedAt());
        assertEquals(ReservationStatus.CONFIRMED, result.getStatus());
    }

    @Test
    void mapEntityWithNullOptionalFieldsToDomain() {
        ReservationEntity entity = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .user(mock(UserEntity.class))
                .service(mock(OfferedServiceEntity.class))
                .employee(mock(EmployeeEntity.class))
                .dateTime(Instant.parse("2025-10-01T10:00:00Z"))
                .price(BigDecimal.valueOf(100))
                .totalPrice(BigDecimal.valueOf(100))
                .createdAt(null)
                .status(null)
                .build();
        when(userMapper.toDomain(any(), eq(mapperFacade))).thenReturn(mock(User.class));
        when(serviceMapper.toDomain(any(), eq(mapperFacade))).thenReturn(mock(OfferedService.class));
        when(employeeMapper.toDomain(any(), eq(mapperFacade))).thenReturn(mock(Employee.class));
        Reservation result = reservationMapper.toDomain(entity, mapperFacade);

        assertNotNull(result.getCreatedAt());
        assertEquals(ReservationStatus.PENDING, result.getStatus());
    }

    @Test
    void mapReservationWithNullOptionalFieldsToEntity() {
        Reservation reservation = Reservation.builder()
                .id(UUID.randomUUID())
                .user(mock(User.class))
                .service(mock(OfferedService.class))
                .employee(mock(Employee.class))
                .dateTime(Instant.parse("2025-10-01T10:00:00Z"))
                .price(BigDecimal.valueOf(100))
                .totalPrice(BigDecimal.valueOf(100))
                .createdAt(null)
                .status(null)
                .build();
        when(userMapper.toEntity(any(), eq(mapperFacade))).thenReturn(mock(UserEntity.class));
        when(serviceMapper.toEntity(any(), eq(mapperFacade))).thenReturn(mock(OfferedServiceEntity.class));
        when(employeeMapper.toEntity(any(), eq(mapperFacade))).thenReturn(mock(EmployeeEntity.class));
        ReservationEntity result = reservationMapper.toEntity(reservation, mapperFacade);

        assertNotNull(result.getCreatedAt());
        assertEquals(ReservationStatus.PENDING, result.getStatus());
    }
}