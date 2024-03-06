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


    // 회원 로그인
    public void login(UserLoginRequestDto requestDto, HttpServletResponse res) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        // 사용자 확인
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        String token = jwtUtil.createToken(user.getEmail(), user.getRole());
        jwtUtil.addJwtToCookie(token, res);
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


}
