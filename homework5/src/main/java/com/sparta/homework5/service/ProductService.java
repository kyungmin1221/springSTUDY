package com.sparta.homework5.service;


import com.sparta.homework5.constant.Role;
import com.sparta.homework5.domain.ProductEntity;
import com.sparta.homework5.domain.UserEntity;
import com.sparta.homework5.dto.ProductDto;
import com.sparta.homework5.exception.CustomException;
import com.sparta.homework5.exception.ErrorCode;
import com.sparta.homework5.repository.ProductRepository;
import com.sparta.homework5.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final S3Uploader s3Uploader;

    // 상품 등록(매니저만)
    @Transactional
    public ProductDto.ProductResponseDto createProduct(ProductDto.ProductRequestDto requestDto) throws IOException {

        UserEntity user = userService.findUserId(requestDto.getUserId());
        MultipartFile image = requestDto.getImage();
        String savedImageUrl = s3Uploader.upload(image,"images");
        ProductEntity product = ProductEntity.builder()
                .productName(requestDto.getProductName())
                .price(requestDto.getPrice())
                .amount(requestDto.getAmount())
                .intro(requestDto.getIntro())
                .category(requestDto.getCategory())
                .imageUrl(savedImageUrl)
                .user(user)
                .build();

        productRepository.save(product);

        return new ProductDto.ProductResponseDto(product);

    }

    // 선택한 상품 조회
    public ProductDto.ProductResponseDto getProduct(Long productId) {
        ProductEntity product = findProductId(productId);
        return new ProductDto.ProductResponseDto(product);

    }

    // 등록된 상품 전체 목록 조회(상품명, 가격 중 기준을 선택할 수 있다.) - 페이지 처리/정렬 처리
    public Page<ProductDto.ProductResponseDto> getAllProduct(UserEntity user, int page, int size, String sortBy, boolean isAsc){
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // 사용자 권한 가져와서 ADMIN 이면 전체 조회, USER 면 본인이 추가한 부분 조회
        Role userRoleEnum = user.getRole();

        Page<ProductEntity> productList;

        if (userRoleEnum == userRoleEnum.USER) {
            // 사용자 권한이 USER 일 경우
            productList = productRepository.findAllByUser(user, pageable);
        } else {
            productList = productRepository.findAll(pageable);
        }

        return productList.map(ProductDto.ProductResponseDto::new);
    }


    public ProductEntity findProductId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }


}
