package com.sparta.homework5.dto;


import com.sparta.homework5.domain.ProductFolderEntity;
import lombok.*;

import java.util.List;

public class ProductFolderDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductFolderResponseDto {

        private Long productId;

        private List<ProductFolderItemDto> items;

        private int quantity;

        private int price;

        public ProductFolderResponseDto(Long productId, int quantity, int price) {
            this.productId = productId;
            this.quantity = quantity;
            this.price = price;
        }

        public ProductFolderResponseDto(ProductFolderEntity productFolder) {
            this.quantity = productFolder.getQuantity();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductFolderItemDto {

        private Long productId;

        private String productName;

        // 상품의 양
        private int quantity;

        // 상품의 가격
        private int price;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductFolderItemPatchDto {
        // 장바구니 상품의 ㅇㅏ이디
        private Long productFolderId;
        // 상품의 양
        private int quantity;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CartItemDto {       // 상품 가격 조회할 때 필요

        private List<ProductFolderItemDto> items;
        private int totalPrice;

    }
}
