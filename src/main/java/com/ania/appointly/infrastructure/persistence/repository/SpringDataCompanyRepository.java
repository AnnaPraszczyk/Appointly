package com.ania.appointly.infrastructure.persistence.repository;
import com.ania.appointly.infrastructure.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataCompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    boolean existsById(@NonNull UUID id);
    @NonNull Optional<CompanyEntity> findById(@NonNull UUID id);
    @NonNull List<CompanyEntity> findAll();
    Optional<CompanyEntity> findByName(String name);
    boolean existsByName(String name);
    @Query("SELECT c FROM CompanyEntity c WHERE c.phone = :phone")
    Optional<CompanyEntity> findByPhone(@Param("phone") String phone);
}
