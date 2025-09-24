package com.ania.appointly.infrastructure.persistence.repository;
import com.ania.appointly.infrastructure.entity.OfferedServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataServiceRepository extends JpaRepository<OfferedServiceEntity, UUID> {
    List<OfferedServiceEntity> findByCompany_Id(UUID companyId);
    Optional<OfferedServiceEntity> findByName(String name);
    @NonNull Page<OfferedServiceEntity> findAll(@NonNull Pageable pageable);
    @Query("SELECT s FROM OfferedServiceEntity s JOIN s.availableEmployees e WHERE e.id = :employeeId")
    List<OfferedServiceEntity> findByEmployeeId(@Param("employeeId") UUID employeeId);
}

