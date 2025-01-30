package com.reservation.service;

import com.reservation.dto.ReviewRequestDto;
import com.reservation.dto.ReviewResponseDto;
import com.reservation.entity.Reservation;
import com.reservation.entity.ReservationStatus;
import com.reservation.entity.Review;
import com.reservation.entity.Store;
import com.reservation.entity.User;
import com.reservation.entity.UserRoleEnum;
import com.reservation.repository.ReservationRepository;
import com.reservation.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;



    public void createReview(ReviewRequestDto requestDto, User user) {
        Reservation reservation = reservationRepository.findByIdAndUser(requestDto.getReservationId(), user);
        if (reservation == null) {
            throw new IllegalArgumentException("예약 정보가 없습니다.");
        }

        Store store = reservation.getStore();

        ReservationStatus status = reservation.getStatus();
        if (status == ReservationStatus.COMPLETED || status == ReservationStatus.CONFIRMED) {
            Review review = new Review(requestDto, user, store);

            reviewRepository.save(review);
        } else {
            throw new IllegalArgumentException("리뷰권한이 없습니다.");
        }

    }

    public List<ReviewResponseDto> getReview(User user) {
        List<Review> reviews = reviewRepository.findByUser(user);

        if (reviews.isEmpty()) {
            throw new IllegalArgumentException("댓글 작성한 유저가 없습니다.");
        }

        List<ReviewResponseDto> responseDtos = new ArrayList<>();

        for (Review review : reviews) {
            responseDtos.add(new ReviewResponseDto(review));
        }

        return responseDtos;
    }

    @Transactional
    public void updateReview(Long id, ReviewRequestDto requestDto, User user) {
        Review review = reviewRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("작성한 리뷰가 없습니다."));

        if(!review.getUser().equals(user)){
            throw new IllegalArgumentException("사용자가 작성한 댓글이 없습니다.");
        }

        review.update(requestDto);
        reviewRepository.save(review);
    }


    @Transactional
    public void deleteReview(Long id, User user) {

        Review review = reviewRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("없는 리뷰 입니다."));

        UserRoleEnum userRoleEnum = user.getRole();

        if (userRoleEnum == UserRoleEnum.PARTNER) {

            // 리뷰가 속한 스토어의 주인이 맞는지 확인
            if (!review.getStore().getUser().equals(user)) {
                throw new IllegalArgumentException("접근 권한이 없습니다.");
            }


        } else if (userRoleEnum == UserRoleEnum.USER) {

            // 리뷰 작성자 본인인지 확인
            if (!review.getUser().equals(user)) {
                throw new IllegalArgumentException("접근 권한이 없습니다.");
            }
        } else{
            throw new IllegalArgumentException("삭제할 권한이 없습니다.");
        }

        reviewRepository.delete(review);
    }
}
