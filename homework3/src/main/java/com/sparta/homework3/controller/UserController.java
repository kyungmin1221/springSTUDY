package com.sparta.homework3.controller;

import com.sparta.homework3.domain.UserEntity;
import com.sparta.homework3.dto.UserLoginRequestDto;
import com.sparta.homework3.dto.UserRegisterRequestDto;
import com.sparta.homework3.dto.UserRegisterResponseDto;
import com.sparta.homework3.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> registerUser(@RequestBody UserRegisterRequestDto requestDto) {
        UserRegisterResponseDto responseDto = userService.registerUser(requestDto);

        return ResponseEntity.ok(responseDto);
    }


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto requestDto, HttpServletResponse response) {
        userService.login(requestDto, response);

        return ResponseEntity.ok("로그인 성공");
    }
}