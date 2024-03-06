package com.sparta.homework3.service;

import com.sparta.homework3.domain.TeacherEntity;
import com.sparta.homework3.domain.UserEntity;
import com.sparta.homework3.dto.TeacherDto;
import com.sparta.homework3.repository.TeacherRepository;
import com.sparta.homework3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;

    // 강사 등록
    @Transactional
    public TeacherEntity createTeacher(TeacherDto.TeacherRequestDto requestDto,Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        TeacherEntity teacher = convertToEntity(requestDto);
        teacher.setUser(user);
        return teacherRepository.save(teacher);
    }


    // 세부 강사 조회
    public TeacherDto.TeacherResponseDto getTeacher(Long teacherId) {
        TeacherEntity teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("조회한 강사를 찾을 수 없습니다 : " + teacherId));

        return convertToDto(teacher);
    }


    // 강사 수정



    // dto -> entity
    private TeacherEntity convertToEntity(TeacherDto.TeacherRequestDto requestDto) {
        TeacherEntity teacher = new TeacherEntity();
        teacher.setName(requestDto.getName());
        teacher.setCareer(requestDto.getCareer());
        teacher.setCompany(requestDto.getCompany());
        teacher.setTel(requestDto.getTel());
        teacher.setIntrodution(requestDto.getIntrodution());

        return teacher;
    }

    private TeacherDto.TeacherResponseDto convertToDto(TeacherEntity teacher) {
        TeacherDto.TeacherResponseDto responseDto = new TeacherDto.TeacherResponseDto();
        responseDto.setId(teacher.getId());
        responseDto.setName(teacher.getName());
        responseDto.setCompany(teacher.getCompany());

        return responseDto;
    }
}
