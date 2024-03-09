package com.sparta.homework4.service;

import com.sparta.homework4.domain.TeacherEntity;
import com.sparta.homework4.domain.UserEntity;
import com.sparta.homework4.dto.TeacherDto;
import com.sparta.homework4.exception.CustomException;
import com.sparta.homework4.exception.ErrorCode;
import com.sparta.homework4.repository.CourseRepository;
import com.sparta.homework4.repository.TeacherRepository;
import com.sparta.homework4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherService {

    private final TeacherRepository teacherRepository;


    // 강사 등록(매니저 권한)
    @Transactional
    public TeacherDto.TeacherResponseDto createTeacher(TeacherDto.TeacherRequestDto requestDto) {
       TeacherEntity teacher = new TeacherEntity(requestDto);
       teacherRepository.save(teacher);

       return new TeacherDto.TeacherResponseDto(teacher);
    }


    // 세부 강사 조회
    public TeacherDto.TeacherResponseDto getTeacher(Long teacherId) {
        TeacherEntity teacher = findTeacherById(teacherId);

        return convertToDto(teacher);
    }


    // 강사 수정
    @Transactional
    public TeacherDto.TeacherResponseDto updateTeacher(Long teacherId, TeacherDto.TeacherPatchDto patchDto) {
        TeacherEntity teacher = findTeacherById(teacherId);

        teacher.setCareer(patchDto.getCareer());
        teacher.setCompany(patchDto.getCompany());
        teacher.setTel(patchDto.getTel());
        teacher.setIntrodution(patchDto.getIntrodution());

        return convertToDto(teacherRepository.save(teacher));
    }

    // 강사 삭제
    @Transactional
    public String deleteTeacher(Long teacherId) {
        teacherRepository.deleteById(teacherId);
        return "선택한 강사의 정보가 성공적으로 삭제 되었습니다.";
    }


    // 교사 ID 검색 최적화
    public TeacherEntity findTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEACHER_NOT_FOUND));
    }

    private TeacherDto.TeacherResponseDto convertToDto(TeacherEntity teacher) {
        TeacherDto.TeacherResponseDto responseDto = new TeacherDto.TeacherResponseDto();
        responseDto.setTeacherName(teacher.getName());
        responseDto.setCompany(teacher.getCompany());

        return responseDto;
    }

}
