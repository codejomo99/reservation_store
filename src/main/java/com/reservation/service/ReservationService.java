package com.reservation.service;

import com.reservation.dto.ReservationRequestDto;
import com.reservation.dto.ReservationResponseDto;
import com.reservation.dto.KioskRequestDto;
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
    public void createReservationKiosk(KioskRequestDto requestDto, User user) {

        // 1. 예약 조회 (ID로 찾고, 사용자 검증)
        Reservation reservation = reservationRepository.findById(requestDto.getReservationId())
                .orElseThrow(() -> new IllegalArgumentException("예약 정보가 없습니다."));

        if (!reservation.getUser().equals(user)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        // 2. 예약 시간과 키오스크 요청 시간 비교
        LocalDateTime reservationTime = reservation.getReservationTime();
        LocalDateTime nowKioskTime = requestDto.getKioskDateTime();

        // 두 시간의 차이를 절대값으로 변환 (양수만 반환)
        long minutesDifference = Math.abs(Duration.between(reservationTime, nowKioskTime).toMinutes());

        // 3. 10분 이내인지 확인
        if (minutesDifference > 10) {
            throw new IllegalArgumentException("10분 이상 차이 나는 예약은 처리할 수 없습니다.");
        }

        // 4. 예약 상태 변경 (Dirty Checking 활용 → save() 불필요)
        reservation.updateStatus(ReservationStatus.COMPLETED);
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

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()->
                new IllegalArgumentException("예약 정보가 없습니다."));

        if(!reservation.getUser().equals(user)){
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        reservationRepository.delete(reservation);
    }
}
