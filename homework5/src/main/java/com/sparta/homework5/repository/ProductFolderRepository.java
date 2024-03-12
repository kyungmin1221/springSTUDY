package com.sparta.homework5.repository;

import com.sparta.homework5.domain.FolderEntity;
import com.sparta.homework5.domain.ProductEntity;
import com.sparta.homework5.domain.ProductFolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductFolderRepository extends JpaRepository<ProductFolderEntity,Long> {
    Optional<ProductFolderEntity> findByProductAndFolder(ProductEntity product, FolderEntity folder);
}
