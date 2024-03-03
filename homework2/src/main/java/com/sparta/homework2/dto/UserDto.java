package com.sparta.homework2.dto;


import com.sparta.homework2.domain.UserEntity;
import jakarta.persistence.Column;
import lombok.*;

public class UserDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserResponseDto {

        private Long id;

        private String username;

        private String gender;

        private String phoneNumber;

        private String address;


        public UserResponseDto(UserEntity user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.gender = user.getGender();
            this.phoneNumber = user.getPhoneNumber();
            this.address = user.getAddress();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserRequestDto {

        private String username;

        private String gender;

        private String securityNumber;

        private String phoneNumber;

        private String address;

    }
}
