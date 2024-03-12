package com.sparta.homework5.repository;

import com.sparta.homework5.domain.ProductEntity;
import com.sparta.homework5.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findAllByUser(UserEntity user, Pageable pageable);
}
