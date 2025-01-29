package com.reservation.dto;

import com.reservation.entity.Reservation;
import com.reservation.entity.ReservationStatus;
import java.time.LocalDateTime;

public class ReservationResponseDto {
    private Long id;
    private LocalDateTime reservationTime;
    private ReservationStatus status;

    public ReservationResponseDto(Reservation reservation) {
        this.id = reservation.getId();;
        this.reservationTime = reservation.getReservationTime();
        this.status = reservation.getStatus();
    }
}
