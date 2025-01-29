package com.reservation.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class kioskRequestDto {

    private Long reservationId;
    private LocalDateTime kioskDateTime;
}
