package com.sparta.homework5.controller;

import com.sparta.homework5.constant.Role;
import com.sparta.homework5.dto.ProductDto;
import com.sparta.homework5.repository.ProductRepository;
import com.sparta.homework5.security.UserDetailsImpl;
import com.sparta.homework5.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    // 상품 등록(매니저만)
    @Operation(summary = "상품 등록" , description = "매니저 권한을 가진 회원이 상품을 등록합니다.")
    @PostMapping("/create")
    @Secured(Role.Authority.ADMIN)
    public ResponseEntity<ProductDto.ProductResponseDto> createProduct(@ModelAttribute @Valid ProductDto.ProductRequestDto requestDto) throws IOException {
        ProductDto.ProductResponseDto responseDto = productService.createProduct(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 선택한 상품 조회
    @Operation(summary = "선택한 상품의 정보 조화" , description = "productId 를 사용하여 상품의 정보를 조회합니다.")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto.ProductResponseDto> getProduct(@PathVariable Long productId) {
        ProductDto.ProductResponseDto responseDto = productService.getProduct(productId);
        return ResponseEntity.ok(responseDto);
    }

    // 등록된 상품 전체 목록 조회(상품명, 가격 중 기준을 선택할 수 있다.)
    @Operation(summary = "상품 전체 조회" , description = "등록되어 있는 상품 전체를 조회합니다.")
    @GetMapping
    public Page<ProductDto.ProductResponseDto> getAllProduct(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.getAllProduct(userDetails.getUser(), page-1,size,sortBy,isAsc);
    }
}
