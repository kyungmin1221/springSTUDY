package com.sparta.homework5.domain;

import com.sparta.homework5.constant.Category;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// 상름
@Entity
@Getter
@NoArgsConstructor
public class ProductEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int amount;     // 상품의 개수(양)

    @Column(nullable = false)
    private String intro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    // ,fetch = FetchType.EAGER 주석
    @OneToMany(mappedBy = "product")
    private List<ItemBag> productFolderList = new ArrayList<>();


    @Builder
    public ProductEntity(String productName, int price, int amount, String intro, Category category,UserEntity user) {
        this.productName = productName;
        this.price = price;
        this.amount = amount;
        this.intro = intro;
        this.category = category;
        this.user = user;
    }

}
