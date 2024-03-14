package com.sparta.homework5.service;


import com.sparta.homework5.domain.FolderEntity;
import com.sparta.homework5.domain.ProductEntity;
import com.sparta.homework5.domain.ItemBag;
import com.sparta.homework5.domain.UserEntity;
import com.sparta.homework5.dto.FolderDto;
import com.sparta.homework5.dto.ItemBagDto;
import com.sparta.homework5.exception.CustomException;
import com.sparta.homework5.exception.ErrorCode;
import com.sparta.homework5.repository.FolderRepository;
import com.sparta.homework5.repository.ItemBagRepository;
import lombok.RequiredArgsConstructor;
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
    private final ItemBagRepository productFolderRepository;


    // 장바구니 생성(회원만) - 추가된 상품은 구매할 만큼의 수량으로 선택 / 여러개 가능
    @Transactional
    public  List<FolderDto.FolderResponseDto> addFolders(List<String> folderNames, UserEntity user) {
        // 장바구니 중복 방지 - 이미 존재하는지 여부 확인
        List<FolderEntity> existFolderList = folderRepository.findAllByUserAndFolderNameIn(user, folderNames);


        List<FolderEntity> folderList = new ArrayList<>();
        List<FolderDto.FolderResponseDto> responseDtos = new ArrayList<>();

        for (String folderName : folderNames) {
            // 이미 생성한 폴더가 아닌 경우만 폴더 생성 //
            // 중복된 경우가 없는 경우 로직
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
    public ItemBagDto.ItemBagResponseDto addProductInFolder(Long folderId, Long productId, ItemBagDto.AddItemInFolderDto requestDto, UserEntity user) {

        FolderEntity folder = findFolderId(folderId);
        ProductEntity product = productService.findProductId(productId);
        int quantity = requestDto.getQuantity();
        int price = product.getPrice();     // 설계실수 -> 상품과 장바구니 둘다 가격을 관리함

        // 사용자 권한 확인
        if (!product.getUser().getId().equals(user.getId()) || !folder.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("회원님의 관심상품이 아니거나, 회원님의 장바구니가 아닙니다.");
        }

        // 중복 상품 확인
        Optional<ItemBag> overlapProduct = productFolderRepository.findByProductAndFolder(product, folder);
        if (overlapProduct.isPresent()) {
            throw new IllegalArgumentException("이미 해당 폴더에 상품이 존재합니다.");
        }

        // 상품 폴더 엔티티 생성 및 저장
        ItemBag itemBag = ItemBag.builder()
                .product(product)
                .folder(folder)
                .quantity(quantity)
                .price(price)
                .build();

        productFolderRepository.save(itemBag);

        // 응답 DTO 생성 및 반환
        return new ItemBagDto.ItemBagResponseDto(product.getId(), quantity , price);
    }

    // 장바구니 조회(회원만) - 장바구니에 담긴 상품들의 총 결제 금액확인 가능
    public ItemBagDto.CartItemDto getFolder(Long folderId, UserEntity user) {
        FolderEntity folder = findFolderId(folderId);

        if(!folder.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("해당 장바구니에 접근 권한이 없습니다. 다시 로그인해주세요.");
        }

        // 장바구니에서 상품 다 추출
        List<ItemBag> cartItems = productFolderRepository.findAllByFolderId(folderId);

        // map 연산은 각 item을 새로운 형태로 변환하는 역할을 함
        // item : 스트림의 각 요소 -> ItemBagDto.AddItemInFolderDto 로 반환
        List<ItemBagDto.ItemBagResponseDto> itemDtos = cartItems.stream().map(item -> {
            int latestPrice = item.getProduct().getPrice(); // 상품의 최신 가격 정보를 직접 가져옴
            return new ItemBagDto.ItemBagResponseDto(
                    item.getProduct().getId(),
                    item.getQuantity(),
                    latestPrice
            );
        }).collect(Collectors.toList());

        // 총 결제금액 계산 (mapToInt : 각 객체를 정수형 결과로 변환하여 새로운 정수형 스트림을 생성
        int totalPrice = itemDtos.stream().mapToInt(
                item -> item.getPrice() * item.getQuantity())
                .sum();

        return new ItemBagDto.CartItemDto(itemDtos, totalPrice);
    }


    // 장바구니 수정(회원만) - 선택한 상품의 수량을 수정할 수 있음 / 무슨 장바구니 ? 무슨 상품 ?
    @Transactional
    public ItemBagDto.ItemInFolderPatchDto updateFolder(Long folderId, Long productId, ItemBagDto.ItemInFolderPatchDto patchDto, UserEntity user) {
        FolderEntity folder = findFolderId(folderId);
        ProductEntity product  = productService.findProductId(productId);

        // 사용자 권한 확인
        if (!folder.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("회원님의 장바구니가 아닙니다");
        }

        ItemBag itemBag = ItemBag.builder()
                .product(product)
                .folder(folder)
                .quantity(patchDto.getQuantity())
                .build();

        productFolderRepository.save(itemBag);

        return new ItemBagDto.ItemInFolderPatchDto(itemBag);
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
            // 원래 있는 장바구니 라면 true 반환
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
