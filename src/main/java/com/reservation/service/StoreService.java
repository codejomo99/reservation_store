package com.reservation.service;


import com.reservation.dto.StoreRequestDto;
import com.reservation.dto.StoreResponseDto;
import com.reservation.entity.Store;
import com.reservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    public StoreResponseDto createStore(StoreRequestDto storeRequestDto) {

        Store store = new Store(storeRequestDto);

        return new StoreResponseDto(storeRepository.save(store));
    }
}
