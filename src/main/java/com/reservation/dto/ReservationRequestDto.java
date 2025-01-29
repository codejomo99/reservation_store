package com.reservation.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReservationRequestDto {


    private Long storeId;

    private LocalDateTime reservationTime;

}
