package com.sparta.homework5.dto;

import com.sparta.homework5.constant.Category;
import com.sparta.homework5.domain.ProductEntity;
import lombok.*;

public class ProductDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductRequestDto {

        private String productName;

        private int price;

        private int amount;

        private String intro;

        private Category category;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductResponseDto {

        private String productName;

        private int price;

        private int amount;

        private String intro;

        private Category category;

        public ProductResponseDto(ProductEntity product) {
            this.productName = product.getProductName();
            this.price = product.getPrice();
            this.amount = product.getAmount();
            this.intro = product.getIntro();
            this.category = product.getCategory();
        }
    }
}
