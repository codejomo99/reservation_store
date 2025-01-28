package com.reservation.service;

import com.reservation.dto.SignupRequestDto;
import com.reservation.entity.User;
import com.reservation.entity.UserRoleEnum;
import com.reservation.repository.UserRepository;
import java.util.Optional;
import javax.swing.text.html.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String PARTNER_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    public User signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

        // 회원 중복
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if(checkUsername.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 회원 이메일
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if(checkEmail.isPresent()){
            throw new IllegalArgumentException("중복된 email 입니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;

        if (requestDto.isPartner()) {
            if (!PARTNER_TOKEN.equals(requestDto.getPartnerToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.PARTNER;
        }

        User user = new User(username,password,email,role);

        return userRepository.save(user);
    }
}
