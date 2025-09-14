package com.ania.appointly.application.service;
import com.ania.appointly.domain.exeptions.EmployeeValidationException;
import com.ania.appointly.domain.model.Company;
import com.ania.appointly.domain.model.Employee;
import com.ania.appointly.domain.model.ScheduleSlot;
import com.ania.appointly.infrastructure.inmemory.InMemoryEmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {
    private EmployeeService employeeService;
    private InMemoryEmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeRepository = new InMemoryEmployeeRepository();
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    void createEmployeeSuccessfully() {
        Employee employee = createSampleEmployee();
        Employee saved = employeeService.createEmployee(employee);

        assertEquals(employee.getId(), saved.getId());
        assertTrue(employeeRepository.existsById(employee.getId()));
    }

    @Test
    void throwExceptionWhenCreatingEmployeeWithExistingId() {
        Employee employee = createSampleEmployee();
        employeeRepository.save(employee);

        assertThrows(EmployeeValidationException.class, () -> employeeService.createEmployee(employee));
    }

    @Test
    void updateEmployeeSuccessfully() {
        Employee employee = createSampleEmployee();
        employeeRepository.save(employee);
        Employee updated = Employee.builder()
                .id(employee.getId())
                .firstName("Updated")
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .company(employee.getCompany())
                .specializations(employee.getSpecializations())
                .schedule(employee.getSchedule())
                .reservations(employee.getReservations())
                .build();
        Employee result = employeeService.updateEmployee(updated);

        assertEquals("Updated", result.getFirstName());
    }

    @Test
    void throwExceptionWhenUpdatingNonExistingEmployee() {
        Employee employee = createSampleEmployee();

        assertThrows(EmployeeValidationException.class, () -> employeeService.updateEmployee(employee));
    }

    @Test
    void deleteEmployeeSuccessfully() {
        Employee employee = createSampleEmployee();
        employeeRepository.save(employee);
        employeeService.deleteEmployee(employee.getId());

        assertFalse(employeeRepository.existsById(employee.getId()));
    }

    @Test
    void throwExceptionWhenDeletingNonExistingEmployee() {
        UUID id = UUID.randomUUID();

        assertThrows(EmployeeValidationException.class, () -> employeeService.deleteEmployee(id));
    }

    @Test
    void returnEmployeesByCompanyId() {
        Company company = createSampleCompany();
        Employee employee = createSampleEmployeeWithCompany(company);
        employeeRepository.save(employee);
        List<Employee> result = employeeService.getEmployeesByCompany(company.getId());

        assertEquals(1, result.size());
    }

    @Test
    void returnEmployeesBySpecialization() {
        Employee employee = createSampleEmployeeWithSpecialization();
        employeeRepository.save(employee);
        List<Employee> result = employeeService.getEmployeesBySpecialization("massage");

        assertEquals(1, result.size());
    }

    @Test
    void returnAvailableSlotsForGivenDay() {
        ScheduleSlot slot1 = ScheduleSlot.builder()
                .day(DayOfWeek.MONDAY)
                .time(LocalTime.of(10, 0))
                .available(true)
                .build();
        ScheduleSlot slot2 = ScheduleSlot.builder()
                .day(DayOfWeek.MONDAY)
                .time(LocalTime.of(11, 0))
                .available(false)
                .build();
        Employee employee = createSampleEmployeeWithSchedule(List.of(slot1, slot2));
        employeeRepository.save(employee);
        List<ScheduleSlot> available = employeeService.getAvailableSlots(employee.getId(), DayOfWeek.MONDAY);

        assertEquals(1, available.size());
        assertTrue(available.getFirst().isAvailable());
    }

    @Test
    void updateEmployeeSchedule() {
        Employee employee = createSampleEmployee();
        employeeRepository.save(employee);
        ScheduleSlot newSlot = ScheduleSlot.builder()
                .day(DayOfWeek.FRIDAY)
                .time(LocalTime.of(14, 0))
                .available(true)
                .build();
        employeeService.updateSchedule(employee.getId(), List.of(newSlot));
        Employee updated = employeeRepository.findById(employee.getId()).orElseThrow();

        assertEquals(1, updated.getSchedule().size());
        assertEquals(DayOfWeek.FRIDAY, updated.getSchedule().getFirst().getDay());
    }

    @Test
    void removeScheduleSlot() {
        ScheduleSlot slot1 = ScheduleSlot.builder()
                .day(DayOfWeek.TUESDAY)
                .time(LocalTime.of(9, 0))
                .available(true)
                .build();
        ScheduleSlot slot2 = ScheduleSlot.builder()
                .day(DayOfWeek.TUESDAY)
                .time(LocalTime.of(10, 0))
                .available(true)
                .build();
        Employee employee = createSampleEmployeeWithSchedule(List.of(slot1, slot2));
        employeeRepository.save(employee);
        employeeService.removeScheduleSlot(employee.getId(), DayOfWeek.TUESDAY, LocalTime.of(9, 0));
        Employee updated = employeeRepository.findById(employee.getId()).orElseThrow();

        assertEquals(1, updated.getSchedule().size());
        assertEquals(LocalTime.of(10, 0), updated.getSchedule().getFirst().getTime());
    }

    @Test
    void throwExceptionWhenEmployeeIdIsNull() {
        assertThrows(EmployeeValidationException.class, () -> Employee.builder()
                .id(null)
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna@example.com")
                .phoneNumber("123456789")
                .company(createSampleCompany())
                .build());
    }

    @Test
    void throwExceptionWhenFirstNameIsBlank() {
        assertThrows(EmployeeValidationException.class, () -> Employee.builder()
                .id(UUID.randomUUID())
                .firstName(" ")
                .lastName("Nowak")
                .email("anna@example.com")
                .phoneNumber("123456789")
                .company(createSampleCompany())
                .build());
    }

    @Test
    void throwExceptionWhenLastNameIsBlank() {
        assertThrows(EmployeeValidationException.class, () -> Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Anna")
                .lastName("")
                .email("anna@example.com")
                .phoneNumber("123456789")
                .company(createSampleCompany())
                .build());
    }

    @Test
    void createEmployeeWithEmptyLists() {
        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna@example.com")
                .phoneNumber("123456789")
                .company(createSampleCompany())
                .build();

        assertNotNull(employee);
        assertTrue(employee.getSpecializations().isEmpty());
        assertTrue(employee.getSchedule().isEmpty());
        assertTrue(employee.getReservations().isEmpty());
    }

    @Test
    void createEmployeeWithLongFields() {
        String longText = "x".repeat(1000);

        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .firstName(longText)
                .lastName(longText)
                .email("long@example.com")
                .phoneNumber("999999999")
                .company(createSampleCompany())
                .build();

        assertEquals(longText, employee.getFirstName());
        assertEquals(longText, employee.getLastName());
    }

    @Test
    void employeesWithSameIdShouldBeEqual() {
        UUID sharedId = UUID.randomUUID();
        Employee emp1 = Employee.builder()
                .id(sharedId)
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna@example.com")
                .phoneNumber("123456789")
                .company(createSampleCompany())
                .build();
        Employee emp2 = Employee.builder()
                .id(sharedId)
                .firstName("Ewa")
                .lastName("Kowalska")
                .email("ewa@example.com")
                .phoneNumber("987654321")
                .company(createSampleCompany())
                .build();

        assertEquals(emp1, emp2);
        assertEquals(emp1.hashCode(), emp2.hashCode());
    }

    @Test
    void employeesWithDifferentIdsShouldNotBeEqual() {
        Employee emp1 = createSampleEmployee();
        Employee emp2 = createSampleEmployee();

        assertNotEquals(emp1, emp2);
        assertNotEquals(emp1.hashCode(), emp2.hashCode());
    }

    @Test
    void employeeShouldBeEqualToItself() {
        Employee emp = createSampleEmployee();
        assertEquals(emp, emp);
    }

    @Test
    void employeeShouldNotBeEqualToNull() {
        Employee emp = createSampleEmployee();
        assertNotEquals(null, emp);
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

    private Employee createSampleEmployee() {
        return Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna@example.com")
                .phoneNumber("123456789")
                .company(createSampleCompany())
                .build();
    }

    private Employee createSampleEmployeeWithCompany(Company company) {
        return Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jan@example.com")
                .phoneNumber("987654321")
                .company(company)
                .build();
    }

    private Employee createSampleEmployeeWithSpecialization() {
        return Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Ewa")
                .lastName("Wiśniewska")
                .email("ewa@example.com")
                .phoneNumber("555555555")
                .company(createSampleCompany())
                .specialization("massage")
                .build();
    }

    private Employee createSampleEmployeeWithSchedule(List<ScheduleSlot> schedule) {
        return Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Tomasz")
                .lastName("Zieliński")
                .email("tom@example.com")
                .phoneNumber("444444444")
                .company(createSampleCompany())
                .schedule(schedule)
                .build();
    }
}