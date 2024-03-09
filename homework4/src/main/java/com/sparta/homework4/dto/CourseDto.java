package com.sparta.homework4.dto;

import com.sparta.homework4.constant.Category;
import com.sparta.homework4.domain.CourseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

public class CourseDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CourseResponseDto {

        private String courseName;

        private int price;

        private String intro;

        private Category category;

        private String teacherName;

        private int likeCount;


        public CourseResponseDto(CourseEntity courseEntity) {
            this.courseName = courseEntity.getName();
            this.price = courseEntity.getPrice();
            this.intro = courseEntity.getIntro();
            this.category = courseEntity.getCategory();
            this.teacherName = courseEntity.getTeacher().getName();
            this.likeCount = courseEntity.getLikeCount();
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

        private Long teacherId;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CoursePatchDto {        // 강의 수정 dto

        @NotBlank
        private String name;

        @Positive
        private int price;

        @NotBlank
        private String intro;

        @NotNull
        private Category category;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CourseCategoryDto {

        private String courseName;

        private int price;

        private String intro;

        private Category category;

        public CourseCategoryDto(CourseEntity courseEntity) {
            this.courseName = courseEntity.getName();
            this.price = courseEntity.getPrice();
            this.intro = courseEntity.getIntro();
            this.category = courseEntity.getCategory();
        }

    }
}
