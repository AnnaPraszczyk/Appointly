package com.ania.appointly.domain.repository;
import com.ania.appointly.domain.model.Reservation;
import org.springframework.data.domain.Pageable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Optional<Reservation> findById(UUID id);
    List<Reservation> findAll();
    boolean existsById(UUID id);
    List<Reservation> findByUserId(UUID userId);
    List<Reservation> findByEmployeeId(UUID employeeId);
    List<Reservation> findByDateTimeBetween(Instant from, Instant to);
    boolean existsByEmployeeIdAndDateTime(UUID employeeId, Instant dateTime);
    List<Reservation> findAllPaged(Pageable pageable);
    void deleteById(UUID id);
}
