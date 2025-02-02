package com.reservation.controller;

import com.reservation.dto.CheckRequestDto;
import com.reservation.dto.ReservationRequestDto;

import com.reservation.dto.ReservationResponseDto;
import com.reservation.dto.KioskRequestDto;
import com.reservation.entity.UserRoleEnum;
import com.reservation.security.UserDetailsImpl;
import com.reservation.service.ReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    // 예약조회
    @GetMapping("/reservation")
    public List<ReservationResponseDto> getReservations(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return reservationService.getReservation(userDetails.getUser());
    }
    // 예약삭제
    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        reservationService.deleteReservation(id,userDetails.getUser());

        return ResponseEntity.ok("Reservation delete successfully");
    }


    // 키오스크 예약 확인
    @PostMapping("/reservation/kiosk")
    public ResponseEntity<String> createReservationKiosk(@RequestBody KioskRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        reservationService.createReservationKiosk(requestDto,userDetails.getUser());

        return ResponseEntity.ok("Reservation created successfully");
    }

    // 점주 예약 정보 확인
    @PostMapping("/partner/reservation")
    public ResponseEntity<String> checkReservation(@RequestBody CheckRequestDto checkRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if(userDetails.getUser().getRole().equals(UserRoleEnum.PARTNER)){
            reservationService.checkReservation(checkRequestDto,userDetails.getUser());
        }else {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        return ResponseEntity.ok("예약확인이 되었습니다.");
    }




}
