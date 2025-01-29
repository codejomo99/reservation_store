package com.reservation.entity;


import com.reservation.dto.ReservationRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime reservationTime;

    @Enumerated(EnumType.STRING)  // Enum을 문자열 형태로 DB에 저장
    private ReservationStatus status;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 예약한 고객

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store; // 예약된 매장

    public Reservation(ReservationRequestDto requestDto,User user, Store store) {
        this.reservationTime = requestDto.getReservationTime();
        this.user = user;
        this.status = ReservationStatus.PENDING;
        this.store = store;
    }

    public void updateStatus(ReservationStatus newStatus) {
        this.status = newStatus;
    }


}
