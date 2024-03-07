package com.sparta.homework3.controller;


import com.sparta.homework3.constant.Role;
import com.sparta.homework3.domain.CourseEntity;
import com.sparta.homework3.domain.TeacherEntity;
import com.sparta.homework3.domain.UserEntity;
import com.sparta.homework3.dto.CourseDto;
import com.sparta.homework3.dto.TeacherDto;
import com.sparta.homework3.service.CourseService;
import com.sparta.homework3.service.TeacherService;
import com.sparta.homework3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final UserService userService;
    private final CourseService courseService;


    // 강사 등록
    @PostMapping
    @Secured(Role.Authority.MANAGER)
//    @PreAuthorize("hasRole('MANAGER')")         // 관리자만 등록이 가능하다
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

    // 강사 수정
    @PatchMapping("/{teacherId}")
    public ResponseEntity<TeacherDto.TeacherResponseDto> updateTeacher(@PathVariable Long teacherId, @RequestBody TeacherDto.TeacherPatchDto patchDto) {
        return ResponseEntity.ok().body(teacherService.updateTeacher(teacherId, patchDto));
    }

    // 강사가 진행하는 모든 강의 목록 조회
    @GetMapping("/{teacherId}/courses")
    @Secured(Role.Authority.MANAGER)
    public ResponseEntity<List<CourseDto.CourseResponseDto>> getAllCourseByTeacher(@PathVariable Long teacherId) {
        List<CourseDto.CourseResponseDto> courses = courseService.getAllCourseByTeacher(teacherId);
        return ResponseEntity.ok(courses);
     }

     // 강사 삭제
    @DeleteMapping("/{teacherId}")
    @Secured(Role.Authority.MANAGER)
    public ResponseEntity<String> deleteTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok().body(teacherService.deleteTeacher(teacherId));
    }

}


