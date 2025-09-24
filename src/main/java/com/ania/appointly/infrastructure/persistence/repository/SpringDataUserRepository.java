package com.ania.appointly.infrastructure.persistence.repository;
import com.ania.appointly.domain.model.Role;
import com.ania.appointly.infrastructure.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findByCompany_Id(UUID companyId);
    List<UserEntity> findByRole(Role role);
    @NonNull Page<UserEntity> findAll(@NonNull Pageable pageable);
}
