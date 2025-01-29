package com.reservation.repository;

import com.reservation.entity.Reservation;
import com.reservation.entity.Store;
import com.reservation.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    Reservation findByUserAndStore(User user, Store store);

    Reservation findByIdAndUser(Long reservationId, User user);

    List<Reservation> findAllByUserId(Long id);

    Reservation findByUserIdAndId(Long id, Long reservationId);
}
