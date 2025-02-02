package com.reservation.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationRequestDto {


    private Long storeId;
    private String userNumber;
    private LocalDateTime reservationTime;

}
