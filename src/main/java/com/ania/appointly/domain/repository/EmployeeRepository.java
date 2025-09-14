package com.ania.appointly.domain.repository;
import com.ania.appointly.domain.model.Employee;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository {
    Employee save(Employee employee); // create or update

    boolean existsById(UUID id);

    Optional<Employee> findById(UUID id);

    List<Employee> findAll();

    List<Employee> findByCompanyId(UUID companyId);

    List<Employee> findBySpecialization(String specialization);

    void deleteById(UUID id);
}
