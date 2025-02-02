package com.reservation.dto;

import com.reservation.entity.ReservationAction;
import lombok.Getter;

@Getter
public class CheckRequestDto {

    private Long reservationId;

    private ReservationAction action;
}
