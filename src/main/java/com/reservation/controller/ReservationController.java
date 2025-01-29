package com.reservation.controller;

import com.reservation.dto.ReservationRequestDto;
import com.reservation.dto.kioskRequestDto;
import com.reservation.security.UserDetailsImpl;
import com.reservation.service.ReservationService;
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
public class ReservationController {

    private final ReservationService reservationService;

    // 예약생성
    @PostMapping("/reservation")
    public ResponseEntity<String> createReservation(
            @RequestBody ReservationRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {

        reservationService.createReservation(requestDto,userDetails.getUser());
        return ResponseEntity.ok("Reservation created successfully");
    }

    // 키오스크 예약 확인
    @PostMapping("/reservation/kiosk")
    public ResponseEntity<String> createReservationKiosk(@RequestBody kioskRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        reservationService.createReservationKiosk(requestDto,userDetails.getUser());

        return ResponseEntity.ok("Reservation created successfully");
    }

    // 예약조회
    // 예약수정
    // 예약삭제





}
