package com.sparta.homework5.dto;

import com.sparta.homework5.domain.FolderEntity;
import com.sparta.homework5.domain.ProductEntity;
import lombok.*;

public class ProductFolderDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductFolderResponseDto {

        private Long productId;

        private Long folderId;

        private int quantity;

        public ProductFolderResponseDto(ProductEntity product, FolderEntity folder, int quantity) {
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductFolderRequestDto {

        private Long productId;

        private int quantity;


    }
}
