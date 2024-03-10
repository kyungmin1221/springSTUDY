package com.sparta.homework4.service;


import com.sparta.homework4.constant.Role;
import com.sparta.homework4.domain.TeacherEntity;
import com.sparta.homework4.domain.UserEntity;
import com.sparta.homework4.dto.UserRegisterDto;
import com.sparta.homework4.exception.CustomException;
import com.sparta.homework4.exception.ErrorCode;
import com.sparta.homework4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public UserRegisterDto.UserRegisterResponseDto registerUser(UserRegisterDto.UserRegisterRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        Role role = requestDto.getRole();
        // 회원 중복 확인
        Optional<UserEntity> checkUserEmail = userRepository.findByEmail(email);
        if (checkUserEmail.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        UserEntity user = new UserEntity(email, password, role);
        userRepository.save(user);

        return new UserRegisterDto.UserRegisterResponseDto(user);

    }

    // 회원탈퇴
    public String deleteUser(Long userId) {
        UserEntity user = findUserById(userId);
        userRepository.delete(user);
        return "회원이 탈퇴되었습니다.";
    }


    public UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
