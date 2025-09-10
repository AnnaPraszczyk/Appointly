package com.ania.appointly.domain.model;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
    private final UUID validId = UUID.randomUUID();

    @Test
    void createReservationWithValidData() {
        Instant now = Instant.now();
        Reservation reservation = Reservation.builder()
                .id(validId)
                .user(mockUser())
                .service(mockService())
                .employee(mockEmployee())
                .dateTime(now)
                .price(BigDecimal.valueOf(100))
                .totalPrice(BigDecimal.valueOf(120))
                .createdAt(now)
                .build();

        assertEquals(validId, reservation.getId());
        assertEquals(mockUser().getEmail(), reservation.getUser().getEmail());
        assertEquals(mockService().getName(), reservation.getService().getName());
        assertEquals(mockEmployee().getEmail(), reservation.getEmployee().getEmail());
        assertEquals(now, reservation.getDateTime());
        assertEquals(BigDecimal.valueOf(100), reservation.getPrice());
        assertEquals(BigDecimal.valueOf(120), reservation.getTotalPrice());
        assertEquals(ReservationStatus.PENDING, reservation.getStatus());
        assertNotNull(reservation.getCreatedAt());
    }

    @Test
    void throwExceptionWhenIdIsNull() {
        assertThrows(NullPointerException.class, () ->
                Reservation.builder()
                        .id(null)
                        .user(mockUser())
                        .service(mockService())
                        .employee(mockEmployee())
                        .dateTime(Instant.now())
                        .price(BigDecimal.valueOf(100))
                        .totalPrice(BigDecimal.valueOf(120))
                        .createdAt(Instant.now())
                        .build());
    }

    @Test
    void throwExceptionWhenUserIsNull() {
        assertThrows(NullPointerException.class, () ->
                Reservation.builder()
                        .id(validId)
                        .user(null)
                        .service(mockService())
                        .employee(mockEmployee())
                        .dateTime(Instant.now())
                        .price(BigDecimal.valueOf(100))
                        .totalPrice(BigDecimal.valueOf(120))
                        .createdAt(Instant.now())
                        .build());
    }

    @Test
    void throwExceptionWhenServiceIsNull() {
        assertThrows(NullPointerException.class, () ->
                Reservation.builder()
                        .id(validId)
                        .user(mockUser())
                        .service(null)
                        .employee(mockEmployee())
                        .dateTime(Instant.now())
                        .price(BigDecimal.valueOf(100))
                        .totalPrice(BigDecimal.valueOf(120))
                        .createdAt(Instant.now())
                        .build());
    }

    @Test
    void throwExceptionWhenEmployeeIsNull() {
        assertThrows(NullPointerException.class, () ->
                Reservation.builder()
                        .id(validId)
                        .user(mockUser())
                        .service(mockService())
                        .employee(null)
                        .dateTime(Instant.now())
                        .price(BigDecimal.valueOf(100))
                        .totalPrice(BigDecimal.valueOf(120))
                        .createdAt(Instant.now())
                        .build()
        );
    }
    @Test
    void throwExceptionWhenDateTimeIsNull() {
        assertThrows(NullPointerException.class, () ->
                Reservation.builder()
                        .id(validId)
                        .user(mockUser())
                        .service(mockService())
                        .employee(mockEmployee())
                        .dateTime(null)
                        .price(BigDecimal.valueOf(100))
                        .totalPrice(BigDecimal.valueOf(120))
                        .createdAt(Instant.now())
                        .build()
        );
    }
    @Test
    void throwExceptionWhenPriceIsNull() {
        assertThrows(NullPointerException.class, () ->
                Reservation.builder()
                        .id(validId)
                        .user(mockUser())
                        .service(mockService())
                        .employee(mockEmployee())
                        .dateTime(Instant.now())
                        .price(null)
                        .totalPrice(BigDecimal.valueOf(120))
                        .createdAt(Instant.now())
                        .build()
        );
    }
    @Test
    void throwExceptionWhenTotalPriceIsNull() {
        assertThrows(NullPointerException.class, () ->
                Reservation.builder()
                        .id(validId)
                        .user(mockUser())
                        .service(mockService())
                        .employee(mockEmployee())
                        .dateTime(Instant.now())
                        .price(BigDecimal.valueOf(100))
                        .totalPrice(null)
                        .createdAt(Instant.now())
                        .build()
        );
    }

    private User mockUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .firstName("Jan")
                .lastName("Nowak")
                .email("jan@example.com")
                .phoneNumber("111-222")
                .role(Role.CLIENT)
                .build();
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
    private Service mockService() {
        return Service.builder()
                .id(UUID.randomUUID())
                .name("Haircut")
                .description("Basic haircut")
                .price(BigDecimal.valueOf(50))
                .duration(Duration.ofMinutes(30))
                .company(mockCompany())
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

    @Test
    void setCreatedAtAutomaticallyIfNullPassed() {
        Reservation reservation = Reservation.builder()
                .id(validId)
                .user(mockUser())
                .service(mockService())
                .employee(mockEmployee())
                .dateTime(Instant.now())
                .price(BigDecimal.valueOf(100))
                .totalPrice(BigDecimal.valueOf(120))
                .createdAt(null)
                .build();

        assertNotNull(reservation.getCreatedAt());
    }

    @Test
    void allowTotalPriceGreaterThanPrice() {
        Reservation reservation = Reservation.builder()
                .id(validId)
                .user(mockUser())
                .service(mockService())
                .employee(mockEmployee())
                .dateTime(Instant.now())
                .price(BigDecimal.valueOf(100))
                .totalPrice(BigDecimal.valueOf(120))
                .createdAt(Instant.now())
                .build();

        assertTrue(reservation.getTotalPrice().compareTo(reservation.getPrice()) >= 0);
    }

    @Test
    void compareReservationsById() {
        UUID id = UUID.randomUUID();

        Reservation r1 = Reservation.builder()
                .id(id)
                .user(mockUser())
                .service(mockService())
                .employee(mockEmployee())
                .dateTime(Instant.now())
                .price(BigDecimal.valueOf(100))
                .totalPrice(BigDecimal.valueOf(120))
                .createdAt(Instant.now())
                .build();

        Reservation r2 = Reservation.builder()
                .id(id)
                .user(mockUser())
                .service(mockService())
                .employee(mockEmployee())
                .dateTime(Instant.now())
                .price(BigDecimal.valueOf(100))
                .totalPrice(BigDecimal.valueOf(120))
                .createdAt(Instant.now())
                .build();

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }
}