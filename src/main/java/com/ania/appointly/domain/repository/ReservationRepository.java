package com.ania.appointly.domain.repository;
import com.ania.appointly.domain.model.Reservation;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {
    Reservation save(Reservation reservation); // create or update
    Optional<Reservation> findById(UUID id);
    List<Reservation> findAll();
    boolean existsById(UUID id);
    List<Reservation> findByUserId(UUID userId);
    List<Reservation> findByEmployeeId(UUID employeeId);
    List<Reservation> findByDateTimeBetween(Instant from, Instant to);
    void deleteById(UUID id);
}
