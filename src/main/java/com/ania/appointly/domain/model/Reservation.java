package com.ania.appointly.domain.model;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reservation {
    private final UUID id;
    private final User user;
    private final Service service;
    private final Employee employee;
    private final Instant dateTime;
    private final ReservationStatus status;
    private final BigDecimal price;
    private final BigDecimal totalPrice;
    private final Instant createdAt;

    @Builder
    private Reservation(UUID id, User user, Service service, Employee employee, Instant dateTime,
                        BigDecimal price, BigDecimal totalPrice, Instant createdAt) {
        this.id = Objects.requireNonNull(id, "Reservation id cannot be null.");
        this.user = Objects.requireNonNull(user, "Reservation user cannot be null.");
        this.service = Objects.requireNonNull(service, "Reservation service cannot be null.");
        this.employee = Objects.requireNonNull(employee, "Reservation employee cannot be null.");
        this.dateTime = Objects.requireNonNull(dateTime, "Reservation date time cannot be null.");
        this.price = Objects.requireNonNull(price, "Reservation price cannot be null.");
        this.totalPrice = Objects.requireNonNull(totalPrice, "Reservation total price cannot be null.");
        this.status = ReservationStatus.PENDING;
        this.createdAt = Instant.now();
    }
}
