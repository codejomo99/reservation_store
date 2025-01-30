package com.reservation.ReservationService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.reservation.BaseTest;
import com.reservation.StoreService.StoreServiceTest;
import com.reservation.dto.ReservationRequestDto;
import com.reservation.entity.Reservation;
import com.reservation.entity.Review;
import com.reservation.repository.ReservationRepository;
import com.reservation.repository.ReviewRepository;
import com.reservation.repository.StoreRepository;
import com.reservation.repository.UserRepository;
import com.reservation.service.ReservationService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class ReservationServiceTest extends BaseTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;



    @BeforeEach()
    void setUp(){
        init();
    }

    @Test
    @DisplayName("예약 작성 성공")
    void createReview_success(){
        // given
        Long storeId = savedStore.getId();
        LocalDateTime reservationTime = LocalDateTime.of(2025, 1, 30, 15, 30);  // 2025-01-30 15:30

        ReservationRequestDto requestDto = new ReservationRequestDto(storeId,reservationTime);

        // when
        reservationService.createReservation(requestDto,testUser);

        // then
        Reservation reservation = reservationRepository.findById(1L).orElseThrow(
                ()-> new IllegalArgumentException("예약 정보가 없습니다.")
        );

        // 예약이 정상적으로 생성되었는지 확인
        assertNotNull(reservation, "예약이 생성되지 않았습니다.");

        // 예약 시간과 상태 확인
        // DBd에 저장 될때 미국시간 +2 시간 들어가서 저장되므로
        LocalDateTime checkTime = LocalDateTime.of(2025, 2, 1, 14, 30);
        assertEquals(checkTime, reservation.getReservationTime(), "예약 시간이 일치하지 않습니다.");


    }



}
