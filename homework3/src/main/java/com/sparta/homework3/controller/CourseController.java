package com.sparta.homework3.controller;

import com.sparta.homework3.constant.Category;
import com.sparta.homework3.constant.Role;
import com.sparta.homework3.domain.CourseEntity;
import com.sparta.homework3.dto.CourseDto;
import com.sparta.homework3.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // (관리자만) 강의 등록
    @PostMapping
    @Secured(Role.Authority.MANAGER)
    public ResponseEntity<CourseDto.CourseResponseDto> createCourse(@RequestBody @Valid CourseDto.CourseRequestDto requestDto) {

        CourseEntity course = courseService.createCourse(requestDto);
        CourseDto.CourseResponseDto responseDto = new CourseDto.CourseResponseDto(course);
        return ResponseEntity.ok(responseDto);
    }

    // 세부 강의 조회
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto.CourseResponseDto> getCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok().body(courseService.getCourse(courseId));
    }

    // 강의 수정
    @PatchMapping("/{courseId}")
    public ResponseEntity<CourseDto.CourseResponseDto> updateCourse(@PathVariable Long courseId, @RequestBody @Valid CourseDto.CoursePatchDto requestDto) {
        return ResponseEntity.ok().body(courseService.updateCourse(courseId, requestDto));
    }

    // 선택한 카테고리에 포함된 강의를 조회
    @GetMapping("/category/{category}")
    @Secured(Role.Authority.MANAGER)
    public ResponseEntity<List<CourseDto.CourseResponseDto>> getCoursesByCategory(@PathVariable Category category) {
        return ResponseEntity.ok(courseService.findCoursesByCategory(category));
    }

    // 강의 삭제
    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok().body(courseService.deleteCourse(courseId));
    }


}
