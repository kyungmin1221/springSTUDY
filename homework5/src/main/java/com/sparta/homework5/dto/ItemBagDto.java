package com.sparta.homework5.dto;


import com.sparta.homework5.domain.ItemBag;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

public class ItemBagDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ItemBagResponseDto {

        private Long productId;

        private int quantity;

        private int price;


        public ItemBagResponseDto(ItemBag productFolder) {
            this.quantity = productFolder.getQuantity();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AddItemInFolderDto {

        // 장바구니에 추가할 상품 이름
        @NotNull
        private String productName;

        // 상품의 양
        @NotNull
        private int quantity;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ItemInFolderPatchDto {

        // 상품의 양
        @NotNull
        private int quantity;

        public ItemInFolderPatchDto(ItemBag itemBag) {
            this.quantity = itemBag.getQuantity();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CartItemDto {       // 상품 가격 조회할 때 필요

        private List<ItemBagResponseDto> items;
        private int totalPrice;

    }
}
