package com.reservation.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class KioskRequestDto {

    private Long reservationId;
    private LocalDateTime kioskDateTime;
}
