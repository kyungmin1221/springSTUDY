package com.sparta.homework5.repository;

import com.sparta.homework5.domain.FolderEntity;
import com.sparta.homework5.domain.ProductEntity;
import com.sparta.homework5.domain.ItemBag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemBagRepository extends JpaRepository<ItemBag,Long> {
    Optional<ItemBag> findByProductAndFolder(ProductEntity product, FolderEntity folder);

    List<ItemBag> findAllByFolderId(Long folderId);
}
