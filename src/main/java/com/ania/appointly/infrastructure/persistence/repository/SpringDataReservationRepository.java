package com.ania.appointly.infrastructure.persistence.repository;
import com.ania.appointly.infrastructure.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface SpringDataReservationRepository extends JpaRepository<ReservationEntity, UUID> {
    List<ReservationEntity> findByUser_Id(UUID userId);
    List<ReservationEntity> findByEmployee_Id(UUID employeeId);
    List<ReservationEntity> findByDateTimeBetween(Instant from, Instant to);
    boolean existsByEmployee_IdAndDateTime(UUID employeeId, Instant dateTime);
    @NonNull Page<ReservationEntity> findAll(@NonNull Pageable pageable);
}
