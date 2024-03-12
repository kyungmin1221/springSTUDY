package com.sparta.homework5.controller;


import com.sparta.homework5.dto.FolderDto;
import com.sparta.homework5.dto.ProductFolderDto;
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
    @PostMapping("/{folderId}")
    public ResponseEntity<ProductFolderDto.ProductFolderResponseDto> addProductInFolder(@PathVariable Long folderId, @RequestBody ProductFolderDto.ProductFolderRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProductFolderDto.ProductFolderResponseDto responseDto = folderService.addProductInFolder(folderId, requestDto,userDetails.getUser());
        return ResponseEntity.ok(responseDto);

    }
}
