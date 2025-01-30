package com.reservation;

import com.reservation.entity.Store;
import com.reservation.entity.User;
import com.reservation.entity.UserRoleEnum;
import com.reservation.repository.StoreRepository;
import com.reservation.repository.UserRepository;
import com.reservation.service.StoreService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class BaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;


    public Store savedStore;
    public User testAdmin;
    public User testUser;

    @BeforeEach()
    public void init(){
        testAdmin = new User();
        testAdmin.setUsername("관리자");
        testAdmin.setPassword("1234");
        testAdmin.setEmail("test@naver.com");
        testAdmin.setRole(UserRoleEnum.PARTNER);
        userRepository.save(testAdmin);

        testUser = new User();
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

}
