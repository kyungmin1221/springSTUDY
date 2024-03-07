package com.sparta.homework3.dto;

import com.sparta.homework3.constant.Category;
import com.sparta.homework3.domain.CourseEntity;
import com.sparta.homework3.domain.TeacherEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

public class CourseDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CourseResponseDto {

        private Long id;

        private String name;

        private int price;

        private String intro;

        private Category category;

        private String teacherName;

        public CourseResponseDto(CourseEntity courseEntity) {
            this.id = courseEntity.getId();
            this.name = courseEntity.getName();
            this.price = courseEntity.getPrice();
            this.intro = courseEntity.getIntro();
            this.category = courseEntity.getCategory();
            this.teacherName = courseEntity.getTeachers().getName();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CourseRequestDto {

        @NotBlank
        private String name;

        @Positive
        private int price;

        @NotBlank
        private String intro;

        @NotNull
        private Category category;

        @NotNull
        private Long teacherId; // 교사 ID 추가
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CoursePatchDto {

        @NotBlank
        private String name;

        @Positive
        private int price;

        @NotBlank
        private String intro;

        @NotNull
        private Category category;

    }


}
