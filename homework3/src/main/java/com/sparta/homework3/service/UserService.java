package com.sparta.homework3.service;

import com.sparta.homework3.constant.Department;
import com.sparta.homework3.constant.Role;
import com.sparta.homework3.domain.UserEntity;
import com.sparta.homework3.dto.UserLoginRequestDto;
import com.sparta.homework3.dto.UserLoginResponseDto;
import com.sparta.homework3.dto.UserRegisterRequestDto;
import com.sparta.homework3.dto.UserRegisterResponseDto;
import com.sparta.homework3.jwt.JwtUtil;
import com.sparta.homework3.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
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
    public UserRegisterResponseDto registerUser(UserRegisterRequestDto requestDto) {
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

        return new UserRegisterResponseDto(user);

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
