package com.sparta.homework5.dto;

import com.sparta.homework5.constant.Category;
import com.sparta.homework5.domain.ProductEntity;
import com.sparta.homework5.domain.ProductFolderEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

        private Long userId;

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

        // 상품하나에 여러개의 장바구니가 있을 수 있음 -> 어떤 장바구니에 상품이 들어있는지 알기위해
        private List<FolderDto.FolderResponseDto>  folderResponseDtos = new ArrayList<>();

        public ProductResponseDto(ProductEntity product) {
            this.productName = product.getProductName();
            this.price = product.getPrice();
            this.amount = product.getAmount();
            this.intro = product.getIntro();
            this.category = product.getCategory();
            for (ProductFolderEntity productFolderEntity : product.getProductFolderList()) {
                folderResponseDtos.add(new FolderDto.FolderResponseDto(productFolderEntity.getFolder()));
                
            }
        }
    }
}
