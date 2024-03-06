package com.sparta.homework3.dto;

import com.sparta.homework3.constant.Department;
import com.sparta.homework3.constant.Role;
import com.sparta.homework3.domain.UserEntity;
import lombok.Getter;

@Getter
public class UserRegisterResponseDto {
    private String email;
    private Department department;
    private Role role;

    public UserRegisterResponseDto(UserEntity user) {
        this.email = user.getEmail();
        this.department = user.getDepartment();
        this.role = user.getRole();
    }
}
