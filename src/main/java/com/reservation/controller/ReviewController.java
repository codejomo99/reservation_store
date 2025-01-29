package com.reservation.controller;


import com.reservation.dto.ReviewRequestDto;
import com.reservation.security.UserDetailsImpl;
import com.reservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping("/review")
    public ResponseEntity<String> createReview(
            @RequestBody ReviewRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        reviewService.createReview(requestDto,userDetails.getUser());

        return ResponseEntity.ok("success Review !!");
    }

    // 리뷰 조회

    // 리뷰 수정

    // 리뷰 삭제

}
