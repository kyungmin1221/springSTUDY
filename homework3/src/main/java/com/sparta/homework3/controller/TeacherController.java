package com.sparta.homework3.controller;


import com.sparta.homework3.domain.TeacherEntity;
import com.sparta.homework3.domain.UserEntity;
import com.sparta.homework3.dto.TeacherDto;
import com.sparta.homework3.service.TeacherService;
import com.sparta.homework3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructor/register")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final UserService userService;


    // 강사 등록
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")         // 관리자만 등록이 가능하다
    public ResponseEntity<TeacherDto.TeacherResponseDto> createTeacher(@RequestBody TeacherDto.TeacherRequestDto requestDto) {
        // 현재 인증된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        UserEntity user = userService.findByEmail(email);
        Long userId = user.getId();

        TeacherEntity teacher = teacherService.createTeacher(requestDto, userId);
        TeacherDto.TeacherResponseDto responseDto = new TeacherDto.TeacherResponseDto(teacher);
        return ResponseEntity.ok(responseDto);

    }

    // 강사 조회
    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherDto.TeacherResponseDto> getTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok().body(teacherService.getTeacher(teacherId));

    }
}
