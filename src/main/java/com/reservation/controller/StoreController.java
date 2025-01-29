package com.reservation.controller;

import com.reservation.dto.StoreRequestDto;
import com.reservation.dto.StoreResponseDto;
import com.reservation.entity.UserRoleEnum;
import com.reservation.security.UserDetailsImpl;
import com.reservation.service.StoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public StoreResponseDto createStore(@RequestBody StoreRequestDto storeRequestDto, @AuthenticationPrincipal
                                        UserDetailsImpl userDetails){
        if(userDetails.getUser().getRole().equals(UserRoleEnum.PARTNER)){
            return storeService.createStore(storeRequestDto,userDetails.getUser());
        }else {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }
    }

    // Read
    @GetMapping("/stores")
    public List<StoreResponseDto> getStore(){
        return storeService.getStore();
    }

    // Update
    @PutMapping("/stores/{id}")
    public StoreResponseDto updateStore(@PathVariable Long id,
                                        @RequestBody StoreRequestDto storeRequestDto,
                                        @AuthenticationPrincipal
                                        UserDetailsImpl userDetails){

        if(userDetails.getUser().getRole().equals(UserRoleEnum.PARTNER)){
            return storeService.updateStore(id,storeRequestDto,userDetails.getUser());
        }else{
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }


    }

    // Delete
    @DeleteMapping("/stores/{id}")
    public void deleteStore(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){

        if(userDetails.getUser().getRole().equals(UserRoleEnum.PARTNER)){
            storeService.deleteStore(id,userDetails.getUser());
        }else{
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }


    }
}
