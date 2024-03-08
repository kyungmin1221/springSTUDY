package com.sparta.homework4.dto;

import com.sparta.homework4.constant.Department;
import com.sparta.homework4.constant.Role;
import com.sparta.homework4.domain.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class UserLoginDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserLoginRequestDto {

        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserLoginResponseDto {

        private String email;

        private Department department;

        private Role role;

        public UserLoginResponseDto(UserEntity user) {

            this.email = user.getEmail();
            this.department = user.getDepartment();
            this.role = user.getRole();
        }

        public UserLoginResponseDto(String s) {
            System.out.println("로그인 성공");
        }
    }
}
