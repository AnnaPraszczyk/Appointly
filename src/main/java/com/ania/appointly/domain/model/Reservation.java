package com.ania.appointly.domain.model;
import com.ania.appointly.domain.exeptions.ReservationValidationException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reservation {
    @EqualsAndHashCode.Include
    private final UUID id;
    private final User user;
    private final OfferedService service;
    private final Employee employee;
    private final Instant dateTime;
    private final ReservationStatus status;
    private final BigDecimal price;
    private final BigDecimal totalPrice;
    private final Instant createdAt;

    @Builder
    private Reservation(UUID id, User user, OfferedService service, Employee employee, Instant dateTime,
                        BigDecimal price, BigDecimal totalPrice, Instant createdAt, ReservationStatus status) {
        if (id == null) throw new ReservationValidationException("Reservation id cannot be null.");
        this.id = id;
        if (user == null) throw new ReservationValidationException("Reservation user cannot be null.");
        this.user = user;
        if (service == null) throw new ReservationValidationException("Reservation service cannot be null.");
        this.service = service;
        if (employee == null) throw new ReservationValidationException("Reservation employee cannot be null.");
        this.employee = employee;
        if (dateTime == null) throw new ReservationValidationException("Reservation date time cannot be null.");
        this.dateTime = dateTime;
        if (price == null) throw new ReservationValidationException("Reservation price cannot be null.");
        this.price = price;
        if (totalPrice == null) throw new ReservationValidationException("Reservation total price cannot be null.");
        this.totalPrice = totalPrice;
        this.createdAt = createdAt != null ? createdAt : Instant.now();
        this.status = status != null ? status : ReservationStatus.PENDING;
    }
}
