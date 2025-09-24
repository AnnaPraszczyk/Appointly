package com.ania.appointly.application.service;
import com.ania.appointly.domain.exeptions.ReservationValidationException;
import com.ania.appointly.domain.model.*;
import com.ania.appointly.infrastructure.inmemory.InMemoryReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class ReservationServiceTest {
    private ReservationService reservationService;
    private InMemoryReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new InMemoryReservationRepository();
        reservationService = new ReservationService(reservationRepository);
    }

    @Test
    void createReservationSuccessfully() {
        Reservation reservation = createSampleReservation();
        Reservation saved = reservationService.createReservation(reservation);

        assertEquals(reservation.getId(), saved.getId());
        assertTrue(reservationRepository.existsById(reservation.getId()));
    }

    @Test
    void throwExceptionWhenCreatingReservationWithExistingId() {
        Reservation reservation = createSampleReservation();
        reservationRepository.save(reservation);

        assertThrows(ReservationValidationException.class, () -> reservationService.createReservation(reservation));
    }

    @Test
    void updateReservationSuccessfully() {
        Reservation reservation = createSampleReservation();
        reservationRepository.save(reservation);
        Reservation updated = Reservation.builder()
                .id(reservation.getId())
                .user(reservation.getUser())
                .service(reservation.getService())
                .employee(reservation.getEmployee())
                .dateTime(reservation.getDateTime().plusSeconds(3600))
                .price(reservation.getPrice())
                .totalPrice(reservation.getTotalPrice())
                .build();
        Reservation result = reservationService.updateReservation(updated);

        assertEquals(reservation.getId(), result.getId());
        assertEquals(reservation.getDateTime().plusSeconds(3600), result.getDateTime());
    }

    @Test
    void throwExceptionWhenUpdatingNonExistingReservation() {
        Reservation reservation = createSampleReservation();

        assertThrows(ReservationValidationException.class, () -> reservationService.updateReservation(reservation));
    }

    @Test
    void deleteReservationSuccessfully() {
        Reservation reservation = createSampleReservation();
        reservationRepository.save(reservation);
        reservationService.deleteReservation(reservation.getId());

        assertFalse(reservationRepository.existsById(reservation.getId()));
    }

    @Test
    void throwExceptionWhenDeletingNonExistingReservation() {
        UUID id = UUID.randomUUID();

        assertThrows(ReservationValidationException.class, () -> reservationService.deleteReservation(id));
    }

    @Test
    void returnReservationsByUserId() {
        Reservation reservation = createSampleReservation();
        reservationRepository.save(reservation);
        List<Reservation> result = reservationService.getReservationsByUser(reservation.getUser().getId());

        assertEquals(1, result.size());
    }

    @Test
    void returnReservationsByEmployeeId() {
        Reservation reservation = createSampleReservation();
        reservationRepository.save(reservation);
        List<Reservation> result = reservationService.getReservationsByEmployee(reservation.getEmployee().getId());

        assertEquals(1, result.size());
    }

    @Test
    void returnReservationsBetweenDates() {
        Reservation reservation = createSampleReservation();
        reservationRepository.save(reservation);
        Instant from = reservation.getDateTime().minusSeconds(60);
        Instant to = reservation.getDateTime().plusSeconds(60);
        List<Reservation> result = reservationService.getReservationsBetween(from, to);

        assertEquals(1, result.size());
    }

    @Test
    void throwExceptionWhenDateRangeIsInvalid() {
        Instant from = Instant.now().plusSeconds(3600);
        Instant to = Instant.now();

        assertThrows(ReservationValidationException.class, () -> reservationService.getReservationsBetween(from, to));
    }

    @Test
    void reservationsWithSameIdShouldBeEqual() {
        UUID sharedId = UUID.randomUUID();

        Reservation r1 = createReservationWithId(sharedId);
        Reservation r2 = createReservationWithId(sharedId);

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void reservationsWithDifferentIdsShouldNotBeEqual() {
        Reservation r1 = createSampleReservation();
        Reservation r2 = createSampleReservation();

        assertNotEquals(r1, r2);
        assertNotEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void reservationShouldBeEqualToItself() {
        Reservation r = createSampleReservation();
        assertEquals(r, r);
    }

    @Test
    void reservationShouldNotBeEqualToNull() {
        Reservation r = createSampleReservation();
        assertNotEquals(null, r);
    }

    @Test
    void storeUniqueReservationsInSet() {
        UUID id = UUID.randomUUID();
        Reservation r1 = createReservationWithId(id);
        Reservation r2 = createReservationWithId(id); // same ID
        Set<Reservation> set = new HashSet<>();
        set.add(r1);
        set.add(r2);

        assertEquals(1, set.size()); // duplicates by ID are ignored
    }

    @Test
    void retrieveReservationFromMapById() {
        Reservation r = createSampleReservation();
        Map<UUID, Reservation> map = new HashMap<>();
        map.put(r.getId(), r);

        assertEquals(r, map.get(r.getId()));
    }

    @Test
    void setCreatedAtToCurrentTime() {
        Instant before = Instant.now();
        Reservation r = createSampleReservation();
        Instant after = Instant.now();

        assertFalse(r.getCreatedAt().isBefore(before));
        assertFalse(r.getCreatedAt().isAfter(after));
    }

    @Test
    void acceptFutureDateTime() {
        Instant future = Instant.now().plus(Duration.ofDays(1));
        Reservation r = createReservationWithDate(future);

        assertEquals(future, r.getDateTime());
    }

    @Test
    void acceptPositivePriceAndTotalPrice() {
        Reservation r = createSampleReservation();

        assertTrue(r.getPrice().compareTo(BigDecimal.ZERO) > 0);
        assertTrue(r.getTotalPrice().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void throwExceptionWhenPriceIsNull() {
        Company company = createSampleCompany();
        Employee employee = createSampleEmployee(company);
        OfferedService service = createSampleService(company, employee);
        User user = createSampleUser();

        assertThrows(ReservationValidationException.class, () -> Reservation.builder()
                .id(UUID.randomUUID())
                .user(user)
                .service(service)
                .employee(employee)
                .dateTime(Instant.now())
                .price(null)
                .totalPrice(BigDecimal.valueOf(100))
                .build());
    }

    private User createSampleUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna@example.com")
                .phoneNumber("123456789")
                .role(Role.CLIENT)
                .company(null)
                .build();
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
                .lastName("Wiśniewska")
                .email("ewa@example.com")
                .phoneNumber("987654321")
                .company(company)
                .build();
    }

    private OfferedService createSampleService(Company company, Employee employee) {
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

    private Reservation createSampleReservation() {
        User user = createSampleUser();
        Company company = createSampleCompany();
        Employee employee = createSampleEmployee(company);
        OfferedService service = createSampleService(company, employee);

        return Reservation.builder()
                .id(UUID.randomUUID())
                .user(user)
                .service(service)
                .employee(employee)
                .dateTime(Instant.now())
                .price(BigDecimal.valueOf(100))
                .totalPrice(BigDecimal.valueOf(100))
                .build();
    }

    private Reservation createReservationWithId(UUID id) {
        Company company = createSampleCompany();
        Employee employee = createSampleEmployee(company);
        OfferedService service = createSampleService(company, employee);
        User user = createSampleUser();

        return Reservation.builder()
                .id(id)
                .user(user)
                .service(service)
                .employee(employee)
                .dateTime(Instant.now())
                .price(BigDecimal.valueOf(100))
                .totalPrice(BigDecimal.valueOf(100))
                .build();
    }

    private Reservation createReservationWithDate(Instant dateTime) {
        Company company = createSampleCompany();
        Employee employee = createSampleEmployee(company);
        OfferedService service = createSampleService(company, employee);
        User user = createSampleUser();

        return Reservation.builder()
                .id(UUID.randomUUID())
                .user(user)
                .service(service)
                .employee(employee)
                .dateTime(dateTime)
                .price(BigDecimal.valueOf(100))
                .totalPrice(BigDecimal.valueOf(100))
                .build();
    }
}