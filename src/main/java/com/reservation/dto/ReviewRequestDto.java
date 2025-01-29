package com.reservation.dto;


import lombok.Getter;

@Getter
public class ReviewRequestDto {

    private Long reservationId;
    private String content;

}
