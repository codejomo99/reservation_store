package com.reservation.StoreService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.reservation.dto.StoreRequestDto;
import com.reservation.dto.StoreResponseDto;
import com.reservation.entity.Store;
import com.reservation.entity.User;
import com.reservation.entity.UserRoleEnum;
import com.reservation.repository.StoreRepository;
import com.reservation.repository.UserRepository;
import com.reservation.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class StoreServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreService storeService;

    private Store savedStore;
    private User testAdmin;

    @BeforeEach()
    void init(){
        testAdmin = new User();
        testAdmin.setUsername("관리자");
        testAdmin.setPassword("1234");
        testAdmin.setEmail("test@naver.com");
        testAdmin.setRole(UserRoleEnum.PARTNER);
        userRepository.save(testAdmin);

        User testUser = new User();
        testUser.setUsername("유저");
        testUser.setPassword("1234");
        testUser.setEmail("user@naver.com");
        testUser.setRole(UserRoleEnum.USER);

        userRepository.save(testUser);

        // 상점 저장
        Store store = new Store();
        store.setName("Original Store");
        store.setLocation("서울 강남구");
        store.setDescription("테스트 상점입니다.");
        store.setUser(testAdmin); // 소유자 설정

        savedStore = storeRepository.save(store);
    }


    @Test
    @DisplayName("상점 업데이트")
    void updateStore_Success() {
        // Given: 업데이트할 데이터
        StoreRequestDto updateRequest = new StoreRequestDto();
        updateRequest.setName("Updated Store");
        updateRequest.setLocation("서울 마포구");
        updateRequest.setDescription("업데이트된 설명입니다.");


        // When: updateStore 실행
        StoreResponseDto responseDto = storeService.updateStore(savedStore.getId(), updateRequest, testAdmin);

        // Then: 결과 검증
        assertNotNull(responseDto);
        assertEquals("Updated Store", responseDto.getName());
        assertEquals("서울 마포구", responseDto.getLocation());
        assertEquals("업데이트된 설명입니다.", responseDto.getDescription());
    }


}
