package com.sparta.homework4.service;


import com.sparta.homework4.constant.Category;
import com.sparta.homework4.domain.CourseEntity;
import com.sparta.homework4.domain.TeacherEntity;
import com.sparta.homework4.dto.CommentDto;
import com.sparta.homework4.dto.CourseDto;
import com.sparta.homework4.exception.CustomException;
import com.sparta.homework4.exception.ErrorCode;
import com.sparta.homework4.repository.CourseRepository;
import com.sparta.homework4.repository.TeacherRepository;
import jdk.jfr.Frequency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherService teacherService;

    // 강의 등록 (관리자만)
    @Transactional
    public CourseDto.CourseResponseDto createCourse(CourseDto.CourseRequestDto requestDto) {
        TeacherEntity teacher = teacherService.findTeacherById(requestDto.getTeacherId());

        CourseEntity course = new CourseEntity();
        course.setName(requestDto.getName());
        course.setPrice(requestDto.getPrice());
        course.setIntro(requestDto.getIntro());
        course.setCategory(requestDto.getCategory());
        course.setLocalDateTime(LocalDateTime.now());
        course.setTeacher(teacher);

        courseRepository.save(course);

        return new CourseDto.CourseResponseDto(course);
    }

     // 강의 정보 수정(강의명,가격,소개,카테고리) - 매니저 권한만
    @Transactional
    public CourseDto.CourseResponseDto updateCourse(Long courseId, CourseDto.CoursePatchDto patchDto) {
        CourseEntity course = findCourseById(courseId);

        course.setName(patchDto.getName());
        course.setPrice(patchDto.getPrice());
        course.setIntro(patchDto.getIntro());
        course.setCategory(patchDto.getCategory());
        courseRepository.save(course);

        return new CourseDto.CourseResponseDto(course);

    }

    // 세부 강의 조회
    public CourseDto.CourseResponseDto getCourse(Long courseId) {
        CourseEntity course = findCourseById(courseId);
        return convertToDto(course);
    }

    // 강의 삭제
    @Transactional
    public String deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
        return "선택한 강의가 성공적으로 삭제되었습니다!";

    }

    // 선택한 강사가 촬영한 강의 목록 조회 기능
    public List<CourseDto.CourseResponseDto> getAllCourseByTeacher(Long teacherId) {
        return courseRepository.findByTeacherIdOrderByLocalDateTimeDesc(teacherId)
                .stream().map(CourseDto.CourseResponseDto::new).toList();
    }


    // 선택한 카테고리에 포함된 강의를 조회
    public List<CourseDto.CourseCategoryDto> findCoursesByCategory(Category category) {
        return courseRepository.findByCategoryOrderByLocalDateTimeDesc(category)
                .stream().map(CourseDto.CourseCategoryDto::new).toList();
    }


    // 강의ID 검색 최적화
    public CourseEntity findCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomException(ErrorCode.COURSE_NOT_FOUND));
    }


    private CourseDto.CourseResponseDto convertToDto(CourseEntity course) {
        CourseDto.CourseResponseDto responseDto = new CourseDto.CourseResponseDto();
        responseDto.setCourseName(course.getName());
        responseDto.setPrice(course.getPrice());
        responseDto.setIntro(course.getIntro());
        responseDto.setCategory(course.getCategory());
        responseDto.setTeacherName(course.getTeacher().getName());
        responseDto.setLikeCount(course.getLikeCount());

        return responseDto;
    }

}
