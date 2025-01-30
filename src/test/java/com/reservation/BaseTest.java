package com.reservation;

import com.reservation.entity.Reservation;
import com.reservation.entity.ReservationStatus;
import com.reservation.entity.Store;
import com.reservation.entity.User;
import com.reservation.entity.UserRoleEnum;
import com.reservation.repository.ReservationRepository;
import com.reservation.repository.StoreRepository;
import com.reservation.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
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

    @Autowired
    private ReservationRepository reservationRepository;


    public Store savedStore;
    public Store savedStore2;
    public User testAdmin;
    public User testUser;
    public User testUser2;
    public Reservation testReservation;
    public Reservation testReservation2;


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


        testUser2 = new User();
        testUser2.setUsername("유저2");
        testUser2.setPassword("1234");
        testUser2.setEmail("user@naver.com2");
        testUser2.setRole(UserRoleEnum.USER);


        userRepository.save(testUser);

        // 상점 저장
        Store store = new Store();
        store.setName("Original Store");
        store.setLocation("서울 강남구");
        store.setDescription("테스트 상점입니다.");
        store.setUser(testAdmin); // 소유자 설정

        Store store2 = new Store();
        store2.setName("Test Store");
        store2.setLocation("서울 성남구");
        store2.setDescription("테스트 상점입니다.");
        store2.setUser(testAdmin); // 소유자 설정

        savedStore = storeRepository.save(store);
        savedStore2 = storeRepository.save(store2);

        // 예약 데이터
        LocalDateTime reservationTime = LocalDateTime.of(2025, 1, 30, 15, 30);  // 2025-01-30 15:30
        Reservation reservation = new Reservation();
        reservation.setReservationTime(reservationTime);
        reservation.setUser(testUser);
        reservation.setStore(savedStore2);
        reservation.setStatus(ReservationStatus.PENDING);
        testReservation = reservationRepository.save(reservation);

        // 예약 데이터
        Reservation reservation1 = new Reservation();
        reservation1.setReservationTime(reservationTime);
        reservation1.setUser(testUser);
        reservation1.setStore(savedStore);
        reservation1.setStatus(ReservationStatus.COMPLETED);
        testReservation2 = reservationRepository.save(reservation1);

    }

}
