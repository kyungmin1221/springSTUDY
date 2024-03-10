package com.sparta.homework4.controller;

import com.sparta.homework4.domain.UserEntity;
import com.sparta.homework4.dto.UserRegisterDto;
import com.sparta.homework4.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<UserRegisterDto.UserRegisterResponseDto> registerUser(@RequestBody @Valid UserRegisterDto.UserRegisterRequestDto requestDto) {
        UserRegisterDto.UserRegisterResponseDto responseDto = userService.registerUser(requestDto);

        return ResponseEntity.ok(responseDto);
    }

    // 회원탈퇴
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable @NotNull Long userId) {
        String user = userService.deleteUser(userId);
        return ResponseEntity.ok(user);
    }

}