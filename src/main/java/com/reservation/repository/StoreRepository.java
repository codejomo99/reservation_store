package com.reservation.repository;

import com.reservation.entity.Store;
import com.reservation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {
    Store findByUserAndName(User user, String name);
}
