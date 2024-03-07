package com.sparta.homework3.service;


import com.sparta.homework3.constant.Category;
import com.sparta.homework3.domain.CourseEntity;
import com.sparta.homework3.domain.TeacherEntity;
import com.sparta.homework3.dto.CourseDto;
import com.sparta.homework3.dto.TeacherDto;
import com.sparta.homework3.repository.CourseRepository;
import com.sparta.homework3.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    // 강의 등록 (관리자만)
    @Transactional
    public CourseEntity createCourse(CourseDto.CourseRequestDto requestDto) {
        TeacherEntity teacher = teacherRepository.findById(requestDto.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("등록된 강사가 없습니다."));

        // CourseEntity 생성
        CourseEntity course = new CourseEntity();
        course.setName(requestDto.getName());
        course.setPrice(requestDto.getPrice());
        course.setIntro(requestDto.getIntro());
        course.setCategory(requestDto.getCategory());
        course.setTeachers(teacher); // 교사 설정

        return courseRepository.save(course); // Course 저장
    }

    // 강의 정보 수정(강의명,가격,소개,카테고리) - 매니저 권한만
    @Transactional
    public CourseDto.CourseResponseDto updateCourse(Long courseId, CourseDto.CoursePatchDto patchDto) {
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의의 정보를 찾을 수 없습니다"));

        course.setName(patchDto.getName());
        course.setPrice(patchDto.getPrice());
        course.setIntro(patchDto.getIntro());
        course.setCategory(patchDto.getCategory());

        return convertToDto(courseRepository.save(course));
    }

    // 세부 강의 조회
    public CourseDto.CourseResponseDto getCourse(Long courseId) {
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 존재하지 않습니다. "));

        return convertToDto(course);
    }

    // 강의 삭제
    @Transactional
    public String deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
        return "선택한 강의가 성공적으로 삭제되었습니다!";

    }


    private CourseDto.CourseResponseDto convertToDto(CourseEntity course) {
        CourseDto.CourseResponseDto responseDto = new CourseDto.CourseResponseDto();
        responseDto.setId(course.getId());
        responseDto.setName(course.getName());
        responseDto.setPrice(course.getPrice());
        responseDto.setIntro(course.getIntro());
        responseDto.setCategory(course.getCategory());
        responseDto.setTeacherName(course.getTeachers().getName());

        return responseDto;
    }

    // 선택한 강사가 촬영한 강의 목록 조회 기능
    public List<CourseDto.CourseResponseDto> getAllCourseByTeacher(Long teacherId) {
        return courseRepository.findByTeachersIdOrderByLocalDateTimeDesc(teacherId)
                .stream().map(CourseDto.CourseResponseDto::new).toList();
    }

    public List<CourseDto.CourseResponseDto> findCoursesByCategory(Category category) {
        return courseRepository.findByCategoryOrderByLocalDateTimeDesc(category)
                .stream().map(CourseDto.CourseResponseDto::new).toList();
    }

}
