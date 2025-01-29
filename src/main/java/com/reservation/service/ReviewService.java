package com.reservation.service;

import com.reservation.dto.ReviewRequestDto;
import com.reservation.entity.Reservation;
import com.reservation.entity.ReservationStatus;
import com.reservation.entity.Review;
import com.reservation.entity.Store;
import com.reservation.entity.User;
import com.reservation.repository.ReservationRepository;
import com.reservation.repository.ReviewRepository;
import com.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public void createReview(ReviewRequestDto requestDto, User user) {
        Reservation reservation = reservationRepository.findByIdAndUser(requestDto.getReservationId(),user);
        if(reservation == null){
            throw new IllegalArgumentException("예약 정보가 없습니다.");
        }

        Store store = reservation.getStore();

        ReservationStatus status = reservation.getStatus();
        if(status == ReservationStatus.COMPLETED || status == ReservationStatus.CONFIRMED){
            Review review = new Review(requestDto,user,store);

            reviewRepository.save(review);
        }else{
            throw new IllegalArgumentException("리뷰권한이 없습니다.");
        }

    }
}
