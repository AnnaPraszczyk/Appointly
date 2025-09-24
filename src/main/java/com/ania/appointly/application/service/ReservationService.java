package com.ania.appointly.application.service;
import com.ania.appointly.application.usecase.reservation.CreateReservationUseCase;
import com.ania.appointly.application.usecase.reservation.DeleteReservationUseCase;
import com.ania.appointly.application.usecase.reservation.ReadReservationUseCase;
import com.ania.appointly.application.usecase.reservation.UpdateReservationUseCase;
import com.ania.appointly.domain.exeptions.ReservationValidationException;
import com.ania.appointly.domain.model.Reservation;
import com.ania.appointly.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService implements CreateReservationUseCase, ReadReservationUseCase, UpdateReservationUseCase, DeleteReservationUseCase {
    private final ReservationRepository reservationRepository;

    @Override
    public Reservation createReservation(Reservation reservation) {
        if (reservationRepository.existsById(reservation.getId())) {
            throw new ReservationValidationException("Reservation with this ID already exists.");
        }
        if (reservationRepository.existsByEmployeeIdAndDateTime(reservation.getEmployee().getId(), reservation.getDateTime())) {
            throw new ReservationValidationException("Employee already has a reservation at this time.");
        }
        return reservationRepository.save(reservation);
    }

    @Override
    public Optional<Reservation> getReservationById(UUID id) {
        return reservationRepository.findById(id);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> getReservationsByUser(UUID userId) {
        return reservationRepository.findByUserId(userId);
    }

    @Override
    public List<Reservation> getReservationsByEmployee(UUID employeeId) {
        return reservationRepository.findByEmployeeId(employeeId);
    }
    @Override
    public List<Reservation> getReservationsBetween(Instant from, Instant to) {
        if (from == null || to == null) {
            throw new ReservationValidationException("Date range cannot be null.");
        }
        if (from.isAfter(to)) {
            throw new ReservationValidationException("Start date must be before end date.");
        }
        return reservationRepository.findByDateTimeBetween(from, to);
    }

    @Override
    public List<Reservation> getPagedReservations(Pageable pageable) {
        return reservationRepository.findAllPaged(pageable);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        if (!reservationRepository.existsById(reservation.getId())) {
            throw new ReservationValidationException("Cannot update non-existing reservation.");
        }
        List<Reservation> existing = reservationRepository.findByEmployeeId(reservation.getEmployee().getId()).stream()
                .filter(r -> r.getDateTime().equals(reservation.getDateTime()) && !r.getId().equals(reservation.getId()))
                .toList();
        if (!existing.isEmpty()) {
            throw new ReservationValidationException("Employee already has a reservation at this time.");
        }
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(UUID id) {
        if (!reservationRepository.existsById(id)) {
            throw new ReservationValidationException("Cannot delete non-existing reservation.");
        }
        reservationRepository.deleteById(id);
    }
}
