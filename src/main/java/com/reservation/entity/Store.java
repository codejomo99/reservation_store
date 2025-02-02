package com.reservation.entity;


import com.reservation.dto.StoreRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Store extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String description;

    private double rating; // 별점

    @Transient // DB에 저장하지 않음 (계산용)
    private Double distance; // 사용자 위치 기반 거리

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 매장을 등록한 파트너

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations; // 매장에 대한 예약 목록

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews; // 매장에 대한 리뷰 목록

    public Store(StoreRequestDto storeRequestDto, User user) {
        this.name = storeRequestDto.getName();
        this.location = storeRequestDto.getLocation();
        this.description = storeRequestDto.getDescription();
        this.user = user;
    }

    public void update(StoreRequestDto storeRequestDto) {
        this.name = storeRequestDto.getName();
        this.location = storeRequestDto.getLocation();
        this.description = storeRequestDto.getDescription();
    }
}
