package com.reservation.service;


import com.reservation.dto.StoreRequestDto;
import com.reservation.dto.StoreResponseDto;
import com.reservation.entity.Store;
import com.reservation.entity.User;
import com.reservation.repository.StoreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    public StoreResponseDto createStore(StoreRequestDto storeRequestDto, User user) {

        // 중복확인 후 저장
        Store existStore = storeRepository.findByUserAndName(user,storeRequestDto.getName());

        // 만약 같은 이름의 가게가 존재하면 예외 처리 (혹은 다른 처리)
        if (existStore != null) {
            throw new IllegalArgumentException("이미 같은 이름의 가게가 존재합니다.");
        }

        Store store = new Store(storeRequestDto,user);

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
