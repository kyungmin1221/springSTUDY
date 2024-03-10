package com.sparta.homework4.controller;


import com.sparta.homework4.constant.Role;
import com.sparta.homework4.domain.TeacherEntity;
import com.sparta.homework4.domain.UserEntity;
import com.sparta.homework4.dto.CourseDto;
import com.sparta.homework4.dto.TeacherDto;
import com.sparta.homework4.service.CourseService;
import com.sparta.homework4.service.TeacherService;
import com.sparta.homework4.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;


    // 강사 등록
    @PostMapping
    @Secured(Role.Authority.ADMIN)
    public ResponseEntity<TeacherDto.TeacherResponseDto> createTeacher(@RequestBody @Valid TeacherDto.TeacherRequestDto requestDto) {
            TeacherDto.TeacherResponseDto responseDto = teacherService.createTeacher(requestDto);
            return ResponseEntity.ok(responseDto);

    }

    // 강사 조회
    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherDto.TeacherResponseDto> getTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok().body(teacherService.getTeacher(teacherId));
    }

    // 강사 수정
    @PatchMapping("/{teacherId}")
    @Secured(Role.Authority.ADMIN)
    public ResponseEntity<TeacherDto.TeacherResponseDto> updateTeacher(@PathVariable Long teacherId, @RequestBody @Valid TeacherDto.TeacherPatchDto patchDto) {
        return ResponseEntity.ok().body(teacherService.updateTeacher(teacherId, patchDto));
    }


     // 강사 삭제
    @DeleteMapping("/{teacherId}")
    @Secured(Role.Authority.ADMIN)
    public ResponseEntity<String> deleteTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok().body(teacherService.deleteTeacher(teacherId));
    }

}


