package com.reservation.entity;

public enum ReservationStatus {
    PENDING("예약중"),
    CONFIRMED("예약 완료"),
    CANCELED("예약 취소"),
    COMPLETED("방문 완료");

    private final String description;

    ReservationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
