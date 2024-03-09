package com.sparta.homework4.dto;

import com.sparta.homework4.domain.TeacherEntity;
import jakarta.validation.constraints.NotBlank;
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

        private String teacherName;

        private Long id;

        private String company;


        public TeacherResponseDto(TeacherEntity teacher) {
            this.id = teacher.getId();
            this.company = teacher.getCompany();
            this.teacherName = teacher.getName();
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
