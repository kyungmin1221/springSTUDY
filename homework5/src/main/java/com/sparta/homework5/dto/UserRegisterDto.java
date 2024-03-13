package com.sparta.homework5.dto;


import com.sparta.homework5.constant.Role;
import com.sparta.homework5.domain.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

public class UserRegisterDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserRegisterRequestDto {
        @Email
        @NotBlank
        private String email;

        @NotBlank
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\|,.<>\\/?]).{8,15}$", message = "비밀번호는 최소 8자 이상, 15자 이하이며, 적어도 하나의 소문자, 대문자, 숫자, 특수문자를 포함해야 합니다.")
        private String password;

        @NotBlank
        private String gender;

        @NotBlank
        private String phoneNumber;

        @NotBlank
        private String address;

        @NotNull
        private Role role;

    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserRegisterResponseDto {
        private String email;
        private String gender;
        private Role role;
        private String token;

        public UserRegisterResponseDto(UserEntity user,String token) {
            this.email = user.getEmail();
            this.gender = user.getGender();
            this.role = user.getRole();
            this.token = token;
        }
    }
}
