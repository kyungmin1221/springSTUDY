package com.sparta.homework3.dto;

import com.sparta.homework3.domain.TeacherEntity;
import lombok.*;

public class TeacherDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TeacherRequestDto {

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
    public static class TeacherResponseDto {

        private Long id;

        private String company;

        private String name;

        public TeacherResponseDto(TeacherEntity teacher) {
            this.id = teacher.getId();
            this.company = teacher.getCompany();
            this.name = teacher.getName();
        }
    }
}
