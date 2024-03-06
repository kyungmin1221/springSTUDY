package com.sparta.homework3.dto;

import com.sparta.homework3.constant.Department;
import lombok.Getter;

@Getter
public class UserRegisterRequestDto {
    private String email;
    private String password;
    private Department department;
}
