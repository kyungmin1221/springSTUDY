package com.sparta.homework2.controller;


import com.sparta.homework2.domain.UserEntity;
import com.sparta.homework2.dto.UserDto;
import com.sparta.homework2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    // 회원 생성
    @PostMapping
    public ResponseEntity<UserDto.UserResponseDto> registerUser(@RequestBody UserDto.UserRequestDto userRequestDto) {
        UserEntity user = userService.createUser(userRequestDto);
        UserDto.UserResponseDto userResponseDto = new UserDto.UserResponseDto(user);
        return ResponseEntity.ok(userResponseDto);
    }

    // 회원 조회
    @GetMapping
    public ResponseEntity<List<UserDto.UserResponseDto>> getUser() {
        List<UserDto.UserResponseDto> users = userService.getUser();
        return ResponseEntity.ok(users);
    }


}
