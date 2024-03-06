package com.sparta.homework3.dto;

import lombok.*;

public class InstructorDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class InstructorRequestDto {

        private String name;

        private int career;

        private String company;

        private String tel;

        private String introdution;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class InstructorResponseDto {

        private Long id;

        private String company;

        private String name;

    }
}
