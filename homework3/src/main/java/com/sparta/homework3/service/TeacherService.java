package com.sparta.homework3.service;

import com.sparta.homework3.domain.CourseEntity;
import com.sparta.homework3.domain.TeacherEntity;
import com.sparta.homework3.domain.UserEntity;
import com.sparta.homework3.dto.TeacherDto;
import com.sparta.homework3.repository.CourseRepository;
import com.sparta.homework3.repository.TeacherRepository;
import com.sparta.homework3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

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
    @Transactional
    public TeacherDto.TeacherResponseDto updateTeacher(Long teacherId, TeacherDto.TeacherPatchDto patchDto) {
        TeacherEntity teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강사의 정보를 찾을 수 없습니다"));

        teacher.setCareer(patchDto.getCareer());
        teacher.setCompany(patchDto.getCompany());
        teacher.setTel(patchDto.getTel());
        teacher.setIntrodution(patchDto.getIntrodution());

        return convertToDto(teacherRepository.save(teacher));
    }




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

        if (teacher.getUser() != null) {
            responseDto.setRole(teacher.getUser().getRole().toString()); // 관리자의 Role 정보를 설정
        }

        return responseDto;
    }

    public TeacherEntity findByName(String name) {
        return teacherRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이름을 가진 강사를 찾을 수 없습니다 : " + name));
    }
}
