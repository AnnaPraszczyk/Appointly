package com.ania.appointly.application.service;
import com.ania.appointly.application.usecase.employee.*;
import com.ania.appointly.domain.exeptions.EmployeeValidationException;
import com.ania.appointly.domain.model.Employee;
import com.ania.appointly.domain.model.ScheduleSlot;
import com.ania.appointly.domain.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService implements CreateEmployeeUseCase, ReadEmployeeUseCase, UpdateEmployeeUseCase, DeleteEmployeeUseCase,
        GetAvailableSlotsUseCase, UpdateEmployeeScheduleUseCase {
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) {
            throw new EmployeeValidationException("Employee with this ID already exists.");
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> getEmployeeById(UUID id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getEmployeesByCompany(UUID companyId) {
        return employeeRepository.findByCompanyId(companyId);
    }

    @Override
    public List<Employee> getEmployeesBySpecialization(String specialization) {
        return employeeRepository.findBySpecialization(specialization);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        if (!employeeRepository.existsById(employee.getId())) {
            throw new EmployeeValidationException("Cannot update non-existing employee.");
        }
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(UUID id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeValidationException("Cannot delete non-existing employee.");
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public List<ScheduleSlot> getAvailableSlots(UUID employeeId, DayOfWeek day) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeValidationException("Employee not found."));

        return employee.getSchedule().stream()
                .filter(slot -> slot.getDay().equals(day) && slot.isAvailable())
                .collect(Collectors.toList());
    }

    @Override
    public void updateSchedule(UUID employeeId, List<ScheduleSlot> newSchedule) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeValidationException("Employee not found."));

        Employee updated = Employee.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .company(employee.getCompany())
                .specializations(employee.getSpecializations())
                .reservations(employee.getReservations())
                .schedule(newSchedule)
                .build();

        employeeRepository.save(updated);
    }

    @Override
    public void removeScheduleSlot(UUID employeeId, DayOfWeek day, LocalTime time) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeValidationException("Employee not found."));

        List<ScheduleSlot> updatedSchedule = employee.getSchedule().stream()
                .filter(slot -> !(slot.getDay().equals(day) && slot.getTime().equals(time)))
                .collect(Collectors.toList());

        Employee updated = Employee.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .company(employee.getCompany())
                .specializations(employee.getSpecializations())
                .reservations(employee.getReservations())
                .schedule(updatedSchedule)
                .build();

        employeeRepository.save(updated);
    }
}
