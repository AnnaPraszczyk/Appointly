package com.ania.appointly.application.usecase.reservation;

import com.ania.appointly.domain.model.Reservation;

public interface UpdateReservationUseCase {
    Reservation updateReservation(Reservation reservation);
}
