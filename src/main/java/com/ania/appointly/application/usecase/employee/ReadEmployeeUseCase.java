package com.ania.appointly.application.usecase.employee;
import com.ania.appointly.domain.model.Employee;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadEmployeeUseCase {
    Optional<Employee> getEmployeeById(UUID id);
    List<Employee> getAllEmployees();
    List<Employee> getEmployeesByCompany(UUID companyId);
    List<Employee> getEmployeesBySpecialization(String specialization);
}
