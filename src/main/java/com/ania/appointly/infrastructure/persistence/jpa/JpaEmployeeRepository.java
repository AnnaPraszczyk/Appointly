package com.ania.appointly.infrastructure.persistence.jpa;
import com.ania.appointly.domain.model.Employee;
import com.ania.appointly.domain.repository.EmployeeRepository;
import com.ania.appointly.infrastructure.entity.EmployeeEntity;
import com.ania.appointly.infrastructure.mapper.MapperFacade;
import com.ania.appointly.infrastructure.persistence.repository.SpringDataEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JpaEmployeeRepository implements EmployeeRepository {
    private final SpringDataEmployeeRepository springRepository;
    private final MapperFacade mappers;

    @Override
    public Employee save(Employee employee) {
        EmployeeEntity entity = mappers.employeeMapper.toEntity(employee, mappers);
        EmployeeEntity saved = springRepository.save(entity);
        return mappers.employeeMapper.toDomain(saved, mappers);
    }

    @Override
    public boolean existsById(UUID id) {
        return springRepository.existsById(id);
    }

    @Override
    public Optional<Employee> findById(UUID id) {
        return springRepository.findById(id)
                .map(entity -> mappers.employeeMapper.toDomain(entity, mappers));
    }

    @Override
    public List<Employee> findAll() {
        return springRepository.findAll().stream()
                .map(entity -> mappers.employeeMapper.toDomain(entity, mappers))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> findByCompanyId(UUID companyId) {
        return springRepository.findByCompany_Id(companyId).stream()
                .map(entity -> mappers.employeeMapper.toDomain(entity, mappers))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> findBySpecialization(String specialization) {
        return springRepository.findBySpecialization(specialization).stream()
                .map(entity -> mappers.employeeMapper.toDomain(entity, mappers))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> findAllPaged(Pageable pageable) {
        return springRepository.findAll(pageable).stream()
                .map(entity -> mappers.employeeMapper.toDomain(entity, mappers))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        springRepository.deleteById(id);
    }
}
