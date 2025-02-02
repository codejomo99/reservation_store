package com.reservation.entity;

public enum ReservationAction {
    CONFIRM("예약 확인"),
    REJECT("예약 취소");

    private final String description;

    ReservationAction(String description){
        this.description = description;
    }

    public String getReservationAction(){
        return description;
    }


}
