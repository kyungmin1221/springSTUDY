package com.sparta.homework3.controller;

import com.sparta.homework3.config.security.UserDetailsImpl;
import com.sparta.homework3.domain.UserEntity;
import com.sparta.homework3.dto.UserLoginRequestDto;
import com.sparta.homework3.dto.UserRegisterRequestDto;
import com.sparta.homework3.dto.UserRegisterResponseDto;
import com.sparta.homework3.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

}