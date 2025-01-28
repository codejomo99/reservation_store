package com.reservation.controller;


import com.reservation.dto.LoginRequestDto;
import com.reservation.dto.SignupRequestDto;
import com.reservation.entity.User;
import com.reservation.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<User> signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(!fieldErrors.isEmpty()){
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                log.error(fieldError.getField() + "필드 : "+fieldError.getDefaultMessage());
            }
        }

        // 회원가입 처리
        User user = userService.signup(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }


}
