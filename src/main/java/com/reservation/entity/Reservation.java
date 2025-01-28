package com.reservation.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    private String Status; // Enum ? String?
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user; // 예약한 고객
//
//    @ManyToOne
//    @JoinColumn(name = "store_id", nullable = false)
//    private Store store; // 예약된 매장
//
//    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
//    private VisitCheck visitCheck; // 방문 확인 정보

}
