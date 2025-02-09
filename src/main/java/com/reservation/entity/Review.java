package com.reservation.entity;


import com.reservation.dto.ReviewRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 리뷰 작성자

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store; // 리뷰 대상 매장

    public Review(ReviewRequestDto requestDto, User user, Store store) {
        this.content = requestDto.getContent();
        this.user = user;
        this.store = store;

    }

    public void update(ReviewRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
