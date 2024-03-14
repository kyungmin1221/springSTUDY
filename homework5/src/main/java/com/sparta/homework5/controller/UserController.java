package com.sparta.homework5.controller;


import com.sparta.homework5.dto.UserRegisterDto;
import com.sparta.homework5.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

@Tag(name = "유저 API", description = "유저 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입" , description = "회원가입을 진행합니다.")
    @PostMapping("/signup")
    public ResponseEntity<UserRegisterDto.UserRegisterResponseDto> signupUser(@RequestBody @Valid UserRegisterDto.UserRegisterRequestDto requestDto) {
        UserRegisterDto.UserRegisterResponseDto responseDto = userService.signupUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }

}
