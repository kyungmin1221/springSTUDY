package com.sparta.homework5.dto;

import com.sparta.homework5.constant.Category;
import com.sparta.homework5.domain.ProductEntity;
import com.sparta.homework5.domain.ItemBag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class ProductDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductRequestDto {

        @NotNull
        private String productName;

        @NotNull
        @Positive
        private int price;

        @NotNull
        @Positive
        private int amount;

        @NotNull
        private String intro;

        @NotNull
        private Category category;

        @NotNull
        private Long userId;

        @NotNull
        private MultipartFile image;

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

        private String imageUrl;


        public ProductResponseDto(ProductEntity product) {
            this.productName = product.getProductName();
            this.price = product.getPrice();
            this.amount = product.getAmount();
            this.intro = product.getIntro();
            this.category = product.getCategory();
            this.imageUrl  = product.getImageUrl();

        }
    }
}
