package com.sparta.homework4.controller;


import com.sparta.homework4.domain.LikeEntity;
import com.sparta.homework4.dto.LikeDto;
import com.sparta.homework4.exception.CustomException;
import com.sparta.homework4.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{userId}/course/{courseId}")
    public ResponseEntity<LikeDto.LikeResponseDto> toggleLike(@PathVariable Long userId, @PathVariable Long courseId ) {
        LikeDto.LikeResponseDto responseDto = likeService.toggleLike(userId,courseId);
        return ResponseEntity.ok(responseDto);
    }
}
