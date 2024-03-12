package com.sparta.homework5.controller;


import com.sparta.homework5.dto.UserRegisterDto;
import com.sparta.homework5.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserRegisterDto.UserRegisterResponseDto> signupUser(@RequestBody @Valid UserRegisterDto.UserRegisterRequestDto requestDto) {
        UserRegisterDto.UserRegisterResponseDto responseDto = userService.signupUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }

}
