package com.reservation.dto;

import com.reservation.entity.Review;
import lombok.Getter;

@Getter
public class ReviewResponseDto {
    String content;

    public ReviewResponseDto(Review review) {
        this.content = review.getContent();
    }
}
