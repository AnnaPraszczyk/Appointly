package com.ania.appointly.infrastructure.persistence.repository;
import com.ania.appointly.infrastructure.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface SpringDataEmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {
    List<EmployeeEntity> findByCompany_Id(UUID companyId);
    @Query("SELECT e FROM EmployeeEntity e JOIN e.specializations s WHERE s = :specialization")
    List<EmployeeEntity> findBySpecialization(@Param("specialization") String specialization);
}

