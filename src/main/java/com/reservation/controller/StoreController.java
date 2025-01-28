package com.reservation.controller;

import com.reservation.dto.StoreRequestDto;
import com.reservation.dto.StoreResponseDto;
import com.reservation.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreController {

    private final StoreService storeService;

    // Create
    @PostMapping("/stores")
    public StoreResponseDto createStore(@RequestBody StoreRequestDto storeRequestDto){
        return storeService.createStore(storeRequestDto);
    }

    // Read

    // Update

    // Delete
}
