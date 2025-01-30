package com.reservation.ReviewService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.reservation.BaseTest;
import com.reservation.dto.ReviewRequestDto;
import com.reservation.entity.Review;
import com.reservation.repository.ReviewRepository;
import com.reservation.service.ReviewService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class ReviewServiceTest extends BaseTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp(){
        init();
    }

    @DisplayName("리뷰 생성")
    @Test
    void create_review(){
        // given
        ReviewRequestDto requestDto = new ReviewRequestDto(testReservation2.getId(), "리뷰를 달았습니다.");

        // when
        reviewService.createReview(requestDto, testUser);

        // then
        Review review = reviewRepository.findByUser_Id(testUser.getId());


        assertEquals("리뷰를 달았습니다.", review.getContent());
    }

    @DisplayName("리뷰 생성 실패 (예약 확인 못해서)")
    @Test
    void create_fail(){
        // given
        ReviewRequestDto requestDto = new ReviewRequestDto(testReservation.getId(), "리뷰를 달았습니다.");

        // when

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.createReview(requestDto, testUser);  // 예약 삭제 수행
        });

        assertEquals("리뷰권한이 없습니다.", thrown.getMessage(), "예상한 예외 메시지가 아닙니다.");

    }



}
