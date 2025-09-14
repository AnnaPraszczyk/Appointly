package com.ania.appointly.infrastructure.inmemory;
import com.ania.appointly.domain.model.Reservation;
import com.ania.appointly.domain.repository.ReservationRepository;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {
    private final Map<UUID, Reservation> storage = new HashMap<>();

    @Override
    public Reservation save(Reservation reservation) {
        storage.put(reservation.getId(), reservation);
        return reservation;
    }

    @Override
    public Optional<Reservation> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(storage.values());
    }
    @Override
    public boolean existsById(UUID id) {
        return storage.containsKey(id);
    }

    @Override
    public List<Reservation> findByUserId(UUID userId) {
        return storage.values().stream()
                .filter(r -> r.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByEmployeeId(UUID employeeId) {
        return storage.values().stream()
                .filter(r -> r.getEmployee().getId().equals(employeeId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByDateTimeBetween(Instant from, Instant to) {
        return storage.values().stream()
                .filter(r -> !r.getDateTime().isBefore(from) && !r.getDateTime().isAfter(to))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        storage.remove(id);
    }
}
