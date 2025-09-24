package com.ania.appointly.infrastructure.persistence.jpa;
import com.ania.appointly.domain.model.Reservation;
import com.ania.appointly.domain.repository.ReservationRepository;
import com.ania.appointly.infrastructure.entity.ReservationEntity;
import com.ania.appointly.infrastructure.mapper.MapperFacade;
import com.ania.appointly.infrastructure.persistence.repository.SpringDataReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JpaReservationRepository implements ReservationRepository {
    private final SpringDataReservationRepository springRepository;
    private final MapperFacade mappers;

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity entity = mappers.reservationMapper.toEntity(reservation, mappers);
        ReservationEntity saved = springRepository.save(entity);
        return mappers.reservationMapper.toDomain(saved, mappers);
    }

    @Override
    public Optional<Reservation> findById(UUID id) {
        return springRepository.findById(id)
                .map(entity -> mappers.reservationMapper.toDomain(entity, mappers));
    }

    @Override
    public List<Reservation> findAll() {
        return springRepository.findAll().stream()
                .map(entity -> mappers.reservationMapper.toDomain(entity, mappers))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(UUID id) {
        return springRepository.existsById(id);
    }

    @Override
    public List<Reservation> findByUserId(UUID userId) {
        return springRepository.findByUser_Id(userId).stream()
                .map(entity -> mappers.reservationMapper.toDomain(entity, mappers))
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByEmployeeId(UUID employeeId) {
        return springRepository.findByEmployee_Id(employeeId).stream()
                .map(entity -> mappers.reservationMapper.toDomain(entity, mappers))
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByDateTimeBetween(Instant from, Instant to) {
        return springRepository.findByDateTimeBetween(from, to).stream()
                .map(entity -> mappers.reservationMapper.toDomain(entity, mappers))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByEmployeeIdAndDateTime(UUID employeeId, Instant dateTime) {
        return springRepository.existsByEmployee_IdAndDateTime(employeeId, dateTime);
    }

    @Override
    public List<Reservation> findAllPaged(Pageable pageable) {
        return springRepository.findAll(pageable).stream()
                .map(entity -> mappers.reservationMapper.toDomain(entity, mappers))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        springRepository.deleteById(id);
    }
}
