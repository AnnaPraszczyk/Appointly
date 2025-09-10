package com.ania.appointly.application.usecase.reservation;
import com.ania.appointly.domain.model.Reservation;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadReservationUseCase {
    Optional<Reservation> getReservationById(UUID id);
    List<Reservation> getAllReservations();
    List<Reservation> getReservationsByUser(UUID userId);
    List<Reservation> getReservationsByEmployee(UUID employeeId);
    List<Reservation> getReservationsBetween(Instant from, Instant to);
}
