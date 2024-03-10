package com.sparta.homework4.dto;

import com.sparta.homework4.domain.LikeEntity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class LikeDto {


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class LikeRequestDto {

        @NotNull
        private Long courseId;

    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class LikeResponseDto {

        private boolean isLiked;

        private String likeMessage;

    }
}
