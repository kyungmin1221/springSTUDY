package com.sparta.homework5.controller;


import com.sparta.homework5.dto.FolderDto;
import com.sparta.homework5.dto.ItemBagDto;
import com.sparta.homework5.security.UserDetailsImpl;
import com.sparta.homework5.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/folder")
public class FolderController {

    private final FolderService folderService;

    // 장바구니 생성
    @PostMapping
    public ResponseEntity<List<FolderDto.FolderResponseDto>> addFolders(@RequestBody FolderDto.FolderRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<String> folderNames = requestDto.getFolderNames();
        List<FolderDto.FolderResponseDto> responseDto = folderService.addFolders(folderNames, userDetails.getUser());

        return ResponseEntity.ok(responseDto);
    }

    // 장바구니에 상품 추가
    @PostMapping("/{folderId}/product/{productId}")
    public ResponseEntity<ItemBagDto.ItemBagResponseDto> addProductInFolder(@PathVariable Long folderId, @PathVariable Long productId,@RequestBody ItemBagDto.AddItemInFolderDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ItemBagDto.ItemBagResponseDto responseDto = folderService.addProductInFolder(folderId, productId,requestDto,userDetails.getUser());
        return ResponseEntity.ok(responseDto);

    }

    // 장바구니 조회
    @GetMapping("/{folderId}")
    public ResponseEntity<ItemBagDto.CartItemDto> getFolder(@PathVariable Long folderId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(folderService.getFolder(folderId, userDetails.getUser()));
    }

    // 장바구니 수정(장바구니에서 선택한 상품의 수량을 수정)
    @PatchMapping("/{folderId}/product/{productId}")
    public ResponseEntity<ItemBagDto.ItemInFolderPatchDto> updateFolder(@PathVariable Long folderId, @PathVariable Long productId,
                                                                      @RequestBody ItemBagDto.ItemInFolderPatchDto patchDto,
                                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(folderService.updateFolder(folderId,productId,patchDto,userDetails.getUser()));
    }

    // 장바구니 삭제
    @DeleteMapping("/{folderId}")
    public ResponseEntity<String> deleteFolder(@PathVariable Long folderId) {
        return ResponseEntity.ok().body(folderService.deleteFolder(folderId));
    }
}
