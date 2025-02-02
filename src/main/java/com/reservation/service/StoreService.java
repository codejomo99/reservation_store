package com.reservation.service;


import com.reservation.dto.StoreRequestDto;
import com.reservation.dto.StoreResponseDto;
import com.reservation.entity.Store;
import com.reservation.entity.User;
import com.reservation.repository.StoreRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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

    public List<StoreResponseDto> getStore(String searchType) {

        List<Sort.Order> sorts = new ArrayList<>();

        // searchType에 따른 정렬 추가
        switch (searchType) {
            case "name":
                sorts.add(Sort.Order.asc("name")); // 이름(가나다순) 오름차순
                break;
            case "rating":
                sorts.add(Sort.Order.desc("rating")); // 별점 내림차순
                break;
            case "distance":
                sorts.add(Sort.Order.asc("distance")); // 거리 오름차순 (가까운 순)
                break;
            default:
                // 기본 정렬 유지
                break;
        }

        // 정렬 조건을 적용하여 데이터 조회
        List<StoreResponseDto> storeList = storeRepository.findAll(Sort.by(sorts))
                .stream()
                .map(StoreResponseDto::new)
                .toList();

        return storeList;
    }

    @Transactional
    public StoreResponseDto updateStore(Long id, StoreRequestDto storeRequestDto, User user) {
        Store store = storeRepository.findByIdAndUser(id,user);
        if(store == null){
            throw new IllegalArgumentException("없는 상점입니다.");
        }
        store.update(storeRequestDto);

        return new StoreResponseDto(store);
    }


    public void deleteStore(Long id, User user) {
        Store store = storeRepository.findByIdAndUser(id,user);

        if(store == null){
            throw new IllegalArgumentException("없는 상점입니다.");
        }

        storeRepository.delete(store);
    }
}
