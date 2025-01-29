package com.reservation.repository;

import com.reservation.entity.Reservation;
import com.reservation.entity.Store;
import com.reservation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    Reservation findByUserAndStore(User user, Store store);
}
