package com.sparta.homework4.dto;

import com.sparta.homework4.domain.CommentEntity;
import com.sparta.homework4.domain.CourseEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class CommentDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CommentRequestDto {

        // 댓글 내용
        @NotBlank
        private String content;

    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CommentResponseDto {

        // 댓글을 담긴 강의 id
        private Long courseId;

        private String content;

        private String message;

        public CommentResponseDto(CommentEntity comment) {
            this.courseId = comment.getCourse().getId();
            this.content = comment.getContent();
        }

        public CommentResponseDto(String message) {
            this.message = message;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CommentPatchDto {

        private String content;
    }
}
