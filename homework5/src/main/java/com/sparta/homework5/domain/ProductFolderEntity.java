package com.sparta.homework5.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "product_folder")
public class ProductFolderEntity {
    // 상품과 장바구니는 다대다 관계임(한 상품은 여러장바구니에 들어갈 수 있음/ 장바구니 하나엔 여러상품이 들어감)
    // 따라서 중간테이블을 만들어 일대다 다대일 관계로 풀어낸다.

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",nullable = false)
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id",nullable = false)
    private FolderEntity folder;

    @Column(nullable = false)
    private int quantity;

    @Builder
    public ProductFolderEntity(ProductEntity product, FolderEntity folder, int quantity) {
        this.product = product;
        this.folder = folder;
        this.quantity = quantity;
    }

}
