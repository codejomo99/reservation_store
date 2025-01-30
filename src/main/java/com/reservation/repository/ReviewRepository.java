package com.reservation.repository;

import com.reservation.entity.Review;
import com.reservation.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByUser(User user);

    Review findByIdAndUser(Long id, User user);

    Review findByUser_Id(Long userId);
}
