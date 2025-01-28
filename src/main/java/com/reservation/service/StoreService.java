package com.reservation.service;


import com.reservation.dto.StoreRequestDto;
import com.reservation.dto.StoreResponseDto;
import com.reservation.entity.Store;
import com.reservation.repository.StoreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    public StoreResponseDto createStore(StoreRequestDto storeRequestDto) {

        Store store = new Store(storeRequestDto);

        return new StoreResponseDto(storeRepository.save(store));
    }

    public List<StoreResponseDto> getStore() {
        List<StoreResponseDto> storeList = storeRepository.findAll().stream()
                .map(StoreResponseDto::new).toList();

        return storeList;
    }

    @Transactional
    public StoreResponseDto updateStore(Long id, StoreRequestDto storeRequestDto) {
        Store store = storeRepository.findById(id).orElseThrow(()->
                new NullPointerException("해당 상점이 없습니다."));

        store.update(storeRequestDto);

        return new StoreResponseDto(store);
    }


    public void deleteStore(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(()->
                new NullPointerException("해당 상점이 없습니다."));

        storeRepository.delete(store);
    }
}
