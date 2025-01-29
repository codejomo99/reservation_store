package com.reservation.dto;


import com.reservation.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StoreResponseDto {


    private String name;
    private String location;
    private String description;


    public StoreResponseDto(Store store) {
        this.name = store.getName();
        this.location = store.getLocation();
        this.description = store.getDescription();
    }
}
