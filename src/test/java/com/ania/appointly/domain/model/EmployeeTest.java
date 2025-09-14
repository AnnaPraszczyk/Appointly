package com.ania.appointly.domain.model;
import com.ania.appointly.domain.exeptions.EmployeeValidationException;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    private final UUID validId = UUID.randomUUID();

    @Test
    void createEmployeeWithValidData() {
        Employee employee = Employee.builder()
                .id(validId)
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna@example.com")
                .phoneNumber("555-123")
                .company(mockCompany())
                .specialization("Haircut")
                .scheduleSlot(mockScheduleSlot())
                .reservation(mockReservation())
                .build();

        assertEquals("Anna", employee.getFirstName());
        assertEquals("Nowak", employee.getLastName());
        assertEquals("anna@example.com", employee.getEmail());
        assertEquals("555-123", employee.getPhoneNumber());
        assertEquals(1, employee.getSpecializations().size());
        assertEquals(1, employee.getSchedule().size());
        assertEquals(1, employee.getReservations().size());
    }

    @Test
    void throwExceptionWhenFirstNameIsBlank() {
        assertThrows(EmployeeValidationException.class, () ->
                Employee.builder()
                        .id(validId)
                        .firstName(" ")
                        .lastName("Nowak")
                        .email("anna@example.com")
                        .phoneNumber("555-123")
                        .company(mockCompany())
                        .build()
        );
    }

    @Test
    void throwExceptionWhenLastNameIsBlank() {
        assertThrows(EmployeeValidationException.class, () ->
                Employee.builder()
                        .id(validId)
                        .firstName("Anna")
                        .lastName(" ")
                        .email("anna@example.com")
                        .phoneNumber("555-123")
                        .company(mockCompany())
                        .build()
        );
    }
    @Test
    void throwExceptionWhenEmailIsBlank() {
        assertThrows(EmployeeValidationException.class, () ->
                Employee.builder()
                        .id(validId)
                        .firstName("Anna")
                        .lastName("Nowak")
                        .email(" ")
                        .phoneNumber("555-123")
                        .company(mockCompany())
                        .build()
        );
    }
    @Test
    void throwExceptionWhenPhoneNumberIsBlank() {
        assertThrows(EmployeeValidationException.class, () ->
                Employee.builder()
                        .id(validId)
                        .firstName("Anna")
                        .lastName("Nowak")
                        .email("anna@example.com")
                        .phoneNumber(" ")
                        .company(mockCompany())
                        .build()
        );
    }
    @Test
    void throwExceptionWhenCompanyIsNull() {
        assertThrows(EmployeeValidationException.class, () ->
                Employee.builder()
                        .id(validId)
                        .firstName("Anna")
                        .lastName("Nowak")
                        .email("anna@example.com")
                        .phoneNumber("555-123")
                        .company(null)
                        .build()
        );
    }
    @Test
    void returnUnmodifiableLists() {
        Employee employee = Employee.builder()
                .id(validId)
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna@example.com")
                .phoneNumber("555-123")
                .company(mockCompany())
                .build();

        assertThrows(UnsupportedOperationException.class, () -> employee.getSpecializations().add("Massage"));
        assertThrows(UnsupportedOperationException.class, () -> employee.getSchedule().add(mockScheduleSlot()));
        assertThrows(UnsupportedOperationException.class, () -> employee.getReservations().add(mockReservation()));
    }

    @Test
    void createEmployeeWithMultipleSpecializationsAndSlots() {
        Employee employee = Employee.builder()
                .id(validId)
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna@example.com")
                .phoneNumber("555-123")
                .company(mockCompany())
                .specialization("Haircut")
                .specialization("Massage")
                .scheduleSlot(mockScheduleSlot())
                .scheduleSlot(ScheduleSlot.builder()
                        .day(java.time.DayOfWeek.TUESDAY)
                        .time(java.time.LocalTime.of(11, 0))
                        .available(true)
                        .build())
                .build();

        assertEquals(2, employee.getSpecializations().size());
        assertEquals(2, employee.getSchedule().size());
    }

    @Test
    void compareEmployeesById() {
        UUID id = UUID.randomUUID();
        Employee e1 = Employee.builder()
                .id(id)
                .firstName("Anna")
                .lastName("Nowak")
                .email("a@a.com")
                .phoneNumber("123")
                .company(mockCompany())
                .build();
        Employee e2 = Employee.builder()
                .id(id)
                .firstName("Other")
                .lastName("Person")
                .email("b@b.com")
                .phoneNumber("456")
                .company(mockCompany())
                .build();

        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
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

    private ScheduleSlot mockScheduleSlot() {
        return ScheduleSlot.builder()
                .day(java.time.DayOfWeek.MONDAY)
                .time(java.time.LocalTime.of(10, 0))
                .available(true)
                .build();
    }
    private Reservation mockReservation() {
        return Reservation.builder()
                .id(UUID.randomUUID())
                .user(mockUser())
                .service(mockService())
                .employee(mockEmployee())
                .dateTime(java.time.Instant.now())
                .price(java.math.BigDecimal.valueOf(100))
                .totalPrice(java.math.BigDecimal.valueOf(100))
                .createdAt(java.time.Instant.now())
                .build();
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
    private OfferedService mockService() {
        return OfferedService.builder()
                .id(UUID.randomUUID())
                .name("Haircut")
                .description("Basic haircut")
                .price(java.math.BigDecimal.valueOf(50))
                .duration(java.time.Duration.ofMinutes(30))
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