package com.sparta.homework3.controller;

import com.sparta.homework3.constant.Category;
import com.sparta.homework3.domain.CourseEntity;
import com.sparta.homework3.domain.TeacherEntity;
import com.sparta.homework3.domain.UserEntity;
import com.sparta.homework3.dto.CourseDto;
import com.sparta.homework3.dto.TeacherDto;
import com.sparta.homework3.service.CourseService;
import com.sparta.homework3.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final TeacherService teacherService;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<CourseDto.CourseResponseDto> createCourse(@RequestBody CourseDto.CourseRequestDto requestDto) {

        CourseEntity course = courseService.createCourse(requestDto);
        CourseDto.CourseResponseDto responseDto = new CourseDto.CourseResponseDto(course);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto.CourseResponseDto> getCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok().body(courseService.getCourse(courseId));
    }

    @PatchMapping("/{courseId}")
    public ResponseEntity<CourseDto.CourseResponseDto> updateCourse(@PathVariable Long courseId, @RequestBody CourseDto.CourseRequestDto requestDto) {
        return ResponseEntity.ok().body(courseService.updateCourse(courseId, requestDto));
    }

    @GetMapping("/category/{category}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<CourseDto.CourseResponseDto>> getCoursesByCategory(@PathVariable Category category) {
        return ResponseEntity.ok(courseService.findCoursesByCategory(category));
    }



}
