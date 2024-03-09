package com.sparta.homework4.controller;


import com.sparta.homework4.domain.CommentEntity;
import com.sparta.homework4.dto.CommentDto;
import com.sparta.homework4.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    // 선택한 강의 댓글 등록
    @PostMapping("/create")
    public ResponseEntity<CommentDto.CommentResponseDto> createComment(@RequestBody CommentDto.CommentRequestDto requestDto,
                                                                       @RequestParam Long userId,
                                                                       @RequestParam Long courseId) {
        CommentDto.CommentResponseDto responseDto = commentService.createComment(userId, courseId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 선택한 강의 댓글 수정

    // 선택한 강의 댓글 삭제
}
