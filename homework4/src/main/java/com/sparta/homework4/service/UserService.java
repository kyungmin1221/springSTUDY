package com.sparta.homework4.service;

import com.sparta.homework4.constant.Department;
import com.sparta.homework4.constant.Role;
import com.sparta.homework4.domain.UserEntity;
import com.sparta.homework4.dto.UserRegisterDto;
import com.sparta.homework4.jwt.JwtUtil;
import com.sparta.homework4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 회원가입
    public UserRegisterDto.UserRegisterResponseDto registerUser(UserRegisterDto.UserRegisterRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        Department department = requestDto.getDepartment();

        // 회원 중복 확인
        Optional<UserEntity> checkUseremail = userRepository.findByEmail(email);
        if (checkUseremail.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        Role role = determineUserRole(department);
        UserEntity user = new UserEntity(email, password, department, role);
        userRepository.save(user);

        return new UserRegisterDto.UserRegisterResponseDto(user);

    }
    // 부서 결정 로직
    private Role determineUserRole(Department department) {
        // 커리큘럼, 개발 부서는 MANAGER 권한
        if (department == Department.CURRICULUM || department == Department.DEVELOPMENT) {
            return Role.MANAGER;
        } else {
            // 그 외는 STAFF 권한
            return Role.STAFF;
        }
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일을 가진 사용자를 찾을 수 없습니다 : " + email));
    }




}
