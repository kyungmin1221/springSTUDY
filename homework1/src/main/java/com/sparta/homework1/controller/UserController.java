package com.sparta.homework1.controller;


import com.sparta.homework1.domain.UserEntity;
import com.sparta.homework1.dto.UserRequestDto;
import com.sparta.homework1.dto.UserResponseDto;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/boards")
public class UserController {

    private final Map<Long, UserEntity> userList = new HashMap<>();
    //게시글 생성
    @PostMapping
    public UserResponseDto createBoard(@RequestBody UserRequestDto userRequestDto) {
        UserEntity user = new UserEntity(userRequestDto);
        Long maxId = userList.size() > 0 ? Collections.max(userList.keySet()) + 1 : 1;
        user.setId(maxId);

        // DB 저장
        userList.put(user.getId(), user);

        UserResponseDto userResponseDto = new UserResponseDto(user);

        return userResponseDto;
    }

    // 게시글 전체 조회 (작성일 기준 내림차순 정렬)
    @GetMapping
    public List<UserResponseDto> getBoard() {
        return userList.values().stream()
                .sorted(Comparator.comparing(UserEntity::getOrderDate).reversed())
                .map(UserResponseDto::new) // entity -> dto
                .collect(Collectors.toList());  // list 저장
    }

    // 선택한 게시물 조회
    @GetMapping("/{boardId}")
    public UserResponseDto getBoardById(@PathVariable Long boardId) {
        UserEntity user = userList.get(boardId);
        if (user == null) {
            return null;
        }
        return new UserResponseDto(user);
    }

    // 게시물 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<?> patchBoard(@PathVariable Long boardId, @RequestBody UserRequestDto userRequestDto) {
        UserEntity userEntity = userList.get(boardId);
        if (userEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시글을 찾을 수 없습니다.");
        }

        if (!userEntity.getPassword().equals(userRequestDto.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
        }

        userEntity.setTitle(userRequestDto.getTitle());
        userEntity.setName(userRequestDto.getName());
        userEntity.setContents(userRequestDto.getContents());

        UserResponseDto responseDto = new UserResponseDto(userEntity);

        return ResponseEntity.ok(responseDto);
    }

    // 게시물 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteMemo(@PathVariable Long boardId, @RequestParam String password) {
        UserEntity userEntity = userList.get(boardId);
        if (userEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시글을 찾을 수 없습니다.");
        }

        if (!userEntity.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
        }

        userList.remove(boardId);
        return ResponseEntity.ok().body(boardId + " 게시글이 삭제되었습니다.");
    }
}
