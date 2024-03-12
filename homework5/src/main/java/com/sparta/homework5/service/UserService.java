package com.sparta.homework5.service;

import com.sparta.homework5.config.PasswordConfig;
import com.sparta.homework5.constant.Role;
import com.sparta.homework5.domain.UserEntity;
import com.sparta.homework5.dto.UserRegisterDto;
import com.sparta.homework5.exception.CustomException;
import com.sparta.homework5.exception.ErrorCode;
import com.sparta.homework5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public UserRegisterDto.UserRegisterResponseDto signupUser(UserRegisterDto.UserRegisterRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String gender = requestDto.getGender();
        String phoneNumber = requestDto.getPhoneNumber();
        String address = requestDto.getAddress();
        Role role = requestDto.getRole();

        Optional<UserEntity> checkUserEmail = userRepository.findByEmail(email);
        // 회원 중복 확인
        if (checkUserEmail.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        UserEntity user = new UserEntity(email,password,gender,phoneNumber,address,role);
        userRepository.save(user);

        return new UserRegisterDto.UserRegisterResponseDto(user);
    }

    public UserEntity findUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

}
