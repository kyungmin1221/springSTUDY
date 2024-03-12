package com.sparta.homework5.service;


import com.sparta.homework5.domain.FolderEntity;
import com.sparta.homework5.domain.ProductEntity;
import com.sparta.homework5.domain.ProductFolderEntity;
import com.sparta.homework5.domain.UserEntity;
import com.sparta.homework5.dto.FolderDto;
import com.sparta.homework5.dto.ProductDto;
import com.sparta.homework5.dto.ProductFolderDto;
import com.sparta.homework5.exception.CustomException;
import com.sparta.homework5.exception.ErrorCode;
import com.sparta.homework5.repository.FolderRepository;
import com.sparta.homework5.repository.ProductFolderRepository;
import com.sparta.homework5.repository.ProductRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FolderService {

    private final FolderRepository folderRepository;
    private final ProductService productService;
    private final ProductFolderRepository productFolderRepository;


    // 장바구니 생성(회원만) - 추가된 상품은 구매할 만큼의 수량으로 선택 / 여러개 가능
    @Transactional
    public  List<FolderDto.FolderResponseDto> addFolders(List<String> folderNames, UserEntity user) {
        // 장바구니 중복 방지
        List<FolderEntity> existFolderList = folderRepository.findAllByUserAndFolderNameIn(user, folderNames);

        List<FolderEntity> folderList = new ArrayList<>();
        List<FolderDto.FolderResponseDto> responseDtos = new ArrayList<>();

        for (String folderName : folderNames) {
            // 이미 생성한 폴더가 아닌 경우만 폴더 생성
            if (!isExistFolderName(folderName, existFolderList)) {
                FolderEntity folder = new FolderEntity(folderName, user);
                folderList.add(folder);
                folderRepository.save(folder); // 폴더를 저장하고
                responseDtos.add(new FolderDto.FolderResponseDto(folder)); // 바로 DTO로 변환하여 추가
            } else { // 중복된 폴더 처리
                throw new CustomException(ErrorCode.DUPLICATE_FOLDER);
            }
        }

        return responseDtos; // 생성된 폴더들의 DTO 리스트 반환
    }

    // 장바구니에 상품을 추가
    @Transactional
    public ProductFolderDto.ProductFolderResponseDto addProductInFolder(Long folderId, ProductFolderDto.ProductFolderItemDto requestDto, UserEntity user) {

        FolderEntity folder = findFolderId(folderId);
        ProductEntity product = productService.findProductId(requestDto.getProductId());
        int quantity = requestDto.getQuantity();
        int price = requestDto.getPrice();

        // 사용자 권한 확인
        if (!product.getUser().getId().equals(user.getId()) || !folder.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("회원님의 관심상품이 아니거나, 회원님의 장바구니가 아닙니다.");
        }

        // 중복 상품 확인
        Optional<ProductFolderEntity> overlapProduct = productFolderRepository.findByProductAndFolder(product, folder);
        if (overlapProduct.isPresent()) {
            throw new IllegalArgumentException("이미 해당 폴더에 상품이 존재합니다.");
        }

        // 상품 폴더 엔티티 생성 및 저장
        ProductFolderEntity productFolder = ProductFolderEntity.builder()
                .product(product)
                .folder(folder)
                .quantity(quantity)
                .price(price)
                .build();

        productFolderRepository.save(productFolder);

        // 응답 DTO 생성 및 반환
        return new ProductFolderDto.ProductFolderResponseDto(product.getId(), quantity , price);
    }

    // 장바구니 조회(회원만) - 장바구니에 담긴 상품들의 총 결제 금액확인 가능
    public ProductFolderDto.CartItemDto getFolder(Long folderId, UserEntity user) {
        FolderEntity folder = findFolderId(folderId);
        if(!folder.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("해당 장바구니에 접근 권한이 없습니다. 다시 로그인해주세요.");
        }

        // 장바구니에서 상품 다 추출
        List<ProductFolderEntity> cartItems = productFolderRepository.findAllByFolderId(folderId);

        List<ProductFolderDto.ProductFolderItemDto> itemDtos = cartItems.stream().map(item -> new ProductFolderDto.ProductFolderItemDto(
                item.getProduct().getId(),      // prio
                item.getProduct().getProductName(),
                item.getQuantity(),
                item.getProduct().getPrice()
        )).collect(Collectors.toList());

        int totalPrice = itemDtos.stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity())
                .sum();

        return new ProductFolderDto.CartItemDto(itemDtos, totalPrice);
    }


    // 장바구니 수정(회원만) - 선택한 상품의 수량을 수정할 수 있음
    @Transactional
    public ProductFolderDto.ProductFolderResponseDto updateFolder(ProductFolderDto.ProductFolderItemPatchDto patchDto, UserEntity user) {
        ProductFolderEntity productFolder = productFolderRepository.findById(patchDto.getProductFolderId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 장바구니 상품이 존재하지 않습니다."));

        if(!productFolder.getFolder().getId().equals(patchDto.getProductFolderId())) {
            throw new IllegalArgumentException("장바구니의 수정 권한이 없습니다. 다시 로그인해주세요.");
        }

        productFolder.setQuantity(patchDto.getQuantity());
        productFolderRepository.save(productFolder);

        return new ProductFolderDto.ProductFolderResponseDto(productFolder);
    }

    // 장바구니 삭제(회원만)
    @Transactional
    public String deleteFolder(Long folderId) {
        folderRepository.deleteById(folderId);
        return "해당 장바구니가 삭제되었습니다.";
    }


    // 해당하는 장바구니가 있는지 유무 확인
    private Boolean isExistFolderName(String folderName, List<FolderEntity> existFolderList) {
        // 기존 폴더 리스트에서 folder name 이 있는지?
        for (FolderEntity existFolder : existFolderList) {
            if (folderName.equals(existFolder.getFolderName())) {
                return true;
            }
        }
        return false;
    }

    public FolderEntity findFolderId(Long folderId) {
       return folderRepository.findById(folderId)
                .orElseThrow(()-> new CustomException(ErrorCode.BASKET_NOT_FOUND));
    }
}
