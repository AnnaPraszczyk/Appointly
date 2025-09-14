package com.ania.appointly.domain.repository;
import com.ania.appointly.domain.model.OfferedService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository {
    OfferedService save(OfferedService service); // create or update
    boolean existsById(UUID id);
    Optional<OfferedService> findById(UUID id);
    List<OfferedService> findAll();
    List<OfferedService> findByCompanyId(UUID companyId);
    List<OfferedService> findByEmployeeId(UUID employeeId);
    void deleteById(UUID id);
}
