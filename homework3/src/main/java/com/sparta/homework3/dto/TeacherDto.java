package com.sparta.homework3.dto;

import com.sparta.homework3.domain.TeacherEntity;
import com.sparta.homework3.domain.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

public class TeacherDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TeacherRequestDto {

        @NotBlank
        private String name;

        @Positive
        private int career;

        @NotBlank
        private String company;

        @NotBlank
        private String tel;

        @NotBlank
        private String introdution;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TeacherResponseDto {

        private Long id;

        private String company;

        private String name;

        private String role;

        public TeacherResponseDto(TeacherEntity teacher) {
            this.id = teacher.getId();
            this.company = teacher.getCompany();
            this.name = teacher.getName();
            this.role = teacher.getUser() != null ? teacher.getUser().getRole().name() : null; // 관리자의 role을 가져옴

        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TeacherPatchDto {

        @Positive
        private int career;

        @NotBlank
        private String company;

        @NotBlank
        private String tel;

        @NotBlank
        private String introdution;
    }
}
