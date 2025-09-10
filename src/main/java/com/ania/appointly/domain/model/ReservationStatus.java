package com.ania.appointly.domain.model;
import lombok.Getter;

@Getter
public enum ReservationStatus {
    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    CANCELLED("Cancelled"),
    COMPLETED("Completed");

    private final String displayName;

    ReservationStatus(String displayName) {
        this.displayName = displayName;
    }
}
