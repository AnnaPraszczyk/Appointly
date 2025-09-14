package com.ania.appointly.infrastructure.inmemory;
import com.ania.appointly.domain.model.Employee;
import com.ania.appointly.domain.repository.EmployeeRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryEmployeeRepository implements EmployeeRepository {

    private final Map<UUID, Employee> storage = new HashMap<>();

    @Override
    public Employee save(Employee employee) {
        storage.put(employee.getId(), employee);
        return employee;
    }

    @Override
    public boolean existsById(UUID id) {
        return storage.containsKey(id);
    }

    @Override
    public Optional<Employee> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Employee> findByCompanyId(UUID companyId) {
        return storage.values().stream()
                .filter(e -> e.getCompany().getId().equals(companyId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> findBySpecialization(String specialization) {
        return storage.values().stream()
                .filter(e -> e.getSpecializations().contains(specialization))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        storage.remove(id);
    }
}
