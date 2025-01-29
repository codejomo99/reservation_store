package com.reservation.service;

import com.reservation.dto.ReservationRequestDto;
import com.reservation.dto.ReservationResponseDto;
import com.reservation.entity.Reservation;
import com.reservation.entity.Store;
import com.reservation.entity.User;
import com.reservation.repository.ReservationRepository;
import com.reservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final StoreRepository storeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationResponseDto createReservation(ReservationRequestDto requestDto, User user) {
        Store store = storeRepository.findById(requestDto.getStoreId()).orElseThrow(()-> new IllegalArgumentException("상점이 없습니다."));

        // 중복확인
        Reservation existiReservation = reservationRepository.findByUserAndStore(user,store);

        // 만약 같은 이름의 가게가 존재하면 예외 처리 (혹은 다른 처리)
        if (existiReservation != null) {
            throw new IllegalArgumentException("이미 같은 예약이 존재합니다.");
        }

        Reservation reservation = reservationRepository.save(new Reservation(requestDto,user,store));

        return new ReservationResponseDto(reservation);
    }
}
