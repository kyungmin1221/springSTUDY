package com.sparta.homework4.controller;

import com.sparta.homework4.constant.Category;
import com.sparta.homework4.constant.Role;
import com.sparta.homework4.dto.CourseDto;
import com.sparta.homework4.service.CourseService;
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
    @Secured(Role.Authority.ADMIN)
    public ResponseEntity<CourseDto.CourseResponseDto> createCourse(@RequestBody @Valid CourseDto.CourseRequestDto requestDto) {
        CourseDto.CourseResponseDto responseDto = courseService.createCourse(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 강의 수정
    @PatchMapping("/{courseId}")
    @Secured(Role.Authority.ADMIN)
    public ResponseEntity<CourseDto.CourseResponseDto> updateCourse(@PathVariable Long courseId, @RequestBody @Valid CourseDto.CoursePatchDto requestDto) {
        return ResponseEntity.ok().body(courseService.updateCourse(courseId, requestDto));
    }

    // 세부 강의 조회
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto.CourseResponseDto> getCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok().body(courseService.getCourse(courseId));
    }

    // 강의 삭제
    @DeleteMapping("/{courseId}")
    @Secured(Role.Authority.ADMIN)
    public ResponseEntity<String> deleteCourse(@PathVariable @Valid Long courseId) {
        return ResponseEntity.ok().body(courseService.deleteCourse(courseId));
    }

    // 선택한 카테고리에 포함된 강의를 조회
    @GetMapping("/category")
    public ResponseEntity<List<CourseDto.CourseCategoryDto>> getCoursesByCategory(@RequestParam Category category,
                                                                                  @RequestParam(required = false, defaultValue = "localDateTime", value = "orderby") String type)  {
        // required = false : 해당 쿼리 param은 요청시 보내지 않아도 상관없음
        // defaultValue = "~~" : 보내지 않는다면 디폴트 값은 "~~" 이다.
        // value : "orderby" : 쿼리 부분에서 키값은 "orderby" 이다.
        List<CourseDto.CourseCategoryDto> categoryDtos = courseService.findCoursesByCategory(category, type);
        return ResponseEntity.ok(categoryDtos);
    }

}
