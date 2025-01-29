package com.reservation.service;

import com.reservation.dto.ReservationRequestDto;
import com.reservation.dto.ReservationResponseDto;
import com.reservation.dto.kioskRequestDto;
import com.reservation.entity.Reservation;
import com.reservation.entity.ReservationStatus;
import com.reservation.entity.Store;
import com.reservation.entity.User;
import com.reservation.repository.ReservationRepository;
import com.reservation.repository.StoreRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final StoreRepository storeRepository;
    private final ReservationRepository reservationRepository;

    public void createReservation(ReservationRequestDto requestDto, User user) {
        Store store = storeRepository.findById(requestDto.getStoreId()).orElseThrow(()-> new IllegalArgumentException("상점이 없습니다."));

        // 중복확인
        Reservation existiReservation = reservationRepository.findByUserAndStore(user,store);

        // 만약 같은 이름의 가게가 존재하면 예외 처리 (혹은 다른 처리)
        if (existiReservation != null) {
            throw new IllegalArgumentException("이미 같은 예약이 존재합니다.");
        }

        reservationRepository.save(new Reservation(requestDto,user,store));
    }


    @Transactional
    public void createReservationKiosk(kioskRequestDto requestDto, User user) {
        Reservation reservation = reservationRepository.findByIdAndUser(requestDto.getReservationId(),user);

        if(reservation == null){
            throw new IllegalArgumentException("예약 정보가 없습니다.");
        }

        LocalDateTime reservationTime = reservation.getReservationTime();
        LocalDateTime nowKioskTime = requestDto.getKioskDateTime();

        // 두 시간의 차이를 계산 (분 단위로)
        Duration duration = Duration.between(reservationTime, nowKioskTime);
        long minutesDifference = duration.toMinutes();  // 분 단위로 차이 계산

        // 차이가 10분 이하일 경우만 진행
        if (minutesDifference <= 10) {
            // 10분 이하일 때만 처리
            reservation.updateStatus(ReservationStatus.COMPLETED);
            reservationRepository.save(reservation);
            // 예약 처리 로직
        } else {
            throw new IllegalArgumentException("10분 이상 차이 나는 예약은 처리할 수 없습니다.");
        }


    }

    public List<ReservationResponseDto> getReservation(User user) {
        List<Reservation> reservationList = reservationRepository.findAllByUserId(user.getId());

        if(reservationList.isEmpty()){
            throw new IllegalArgumentException("예약 정보가 없습니다.");
        }

        List<ReservationResponseDto> responseDtos = new ArrayList<>();

        for(Reservation reservation: reservationList){
            responseDtos.add(new ReservationResponseDto(reservation));
        }

        return responseDtos;
    }

    public void deleteReservation(Long reservationId, User user) {
        Reservation reservation = reservationRepository.findByIdAndUser(reservationId,user);
        if(reservation == null){
            throw new IllegalArgumentException("예약 정보가 없습니다.");
        }
        reservationRepository.deleteById(reservationId);
    }
}
