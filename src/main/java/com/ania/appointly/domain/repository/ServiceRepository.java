package com.ania.appointly.domain.repository;
import com.ania.appointly.domain.model.OfferedService;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository {
    OfferedService save(OfferedService service);
    boolean existsById(UUID id);
    Optional<OfferedService> findById(UUID id);
    List<OfferedService> findAll();
    List<OfferedService> findByCompanyId(UUID companyId);
    List<OfferedService> findByEmployeeId(UUID employeeId);
    Optional<OfferedService> findByName(String name);
    List<OfferedService> findAllPaged(Pageable pageable);
    void deleteById(UUID id);
}
