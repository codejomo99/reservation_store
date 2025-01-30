package com.reservation.controller;


import com.reservation.dto.ReviewRequestDto;
import com.reservation.dto.ReviewResponseDto;
import com.reservation.security.UserDetailsImpl;
import com.reservation.service.ReviewService;
import jakarta.websocket.server.PathParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @GetMapping("/review")
    public List<ReviewResponseDto> getReview(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return reviewService.getReview(userDetails.getUser());
    }

    // 리뷰 수정
    @PutMapping("/review/{id}")
    public ResponseEntity<String> updateReview(
            @PathVariable Long id,
            @RequestBody ReviewRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {

        reviewService.updateReview(id,requestDto,userDetails.getUser());

        return ResponseEntity.ok("수정이 완료되었습니다.");
    }

    // 리뷰 삭제
    @DeleteMapping("/review/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        reviewService.deleteReview(id,userDetails.getUser());

        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }


}
