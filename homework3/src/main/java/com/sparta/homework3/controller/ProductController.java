package com.sparta.homework3.controller;

import com.sparta.homework3.config.security.UserDetailsImpl;
import com.sparta.homework3.domain.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class ProductController {

    @GetMapping("/products")
    public ResponseEntity<?> getProducts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // @Authentication 의 Principal 에 저장된 UserDetailsImpl(사용자정보) 을 가져옴.
        UserEntity user =  userDetails.getUser();
        System.out.println("user.getUsername() = " + user.getEmail());

        return ResponseEntity.ok("UserDetails 정보 반환 성공");
    }
}
