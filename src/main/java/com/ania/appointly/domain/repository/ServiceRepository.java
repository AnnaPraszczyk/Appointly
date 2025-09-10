package com.ania.appointly.domain.repository;
import com.ania.appointly.domain.model.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository {
    Service save(Service service); // create or update
    boolean existsById(UUID id);
    Optional<Service> findById(UUID id);
    List<Service> findAll();
    List<Service> findByCompanyId(UUID companyId);
    List<Service> findByEmployeeId(UUID employeeId);
    void deleteById(UUID id);
}
