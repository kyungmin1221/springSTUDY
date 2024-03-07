package com.sparta.homework3.dto;

import com.sparta.homework3.constant.Department;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserRegisterRequestDto {

    @Email
    @NotBlank
    private String email;

//    @NotBlank
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\|,.<>\\/?]).{8,15}$", message = "비밀번호는 최소 8자 이상, 15자 이하이며, 적어도 하나의 소문자, 대문자, 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @NotNull
    private Department department;
}
