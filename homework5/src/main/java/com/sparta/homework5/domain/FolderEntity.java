package com.sparta.homework5.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


// 장바구니
@Entity
@Getter
@NoArgsConstructor
public class FolderEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    private String folderName;  // 장바구니의 이름

//    @OneToMany(mappedBy = "folder")
//    private List<ProductFolderEntity> productFolders = new ArrayList<>();

    @Builder
    public FolderEntity(String folderName,UserEntity user) {
        this.folderName = folderName;
        this.user = user;
    }
}
