package com.sparta.homework2.service;

import com.sparta.homework2.domain.UserEntity;
import com.sparta.homework2.dto.UserDto;
import com.sparta.homework2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserEntity createUser(UserDto.UserRequestDto userRequestDto) {
        UserEntity user = new UserEntity();
        user.setUsername(userRequestDto.getUsername());
        user.setGender(userRequestDto.getGender());
        user.setSecurityNumber(userRequestDto.getSecurityNumber());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        user.setAddress(userRequestDto.getAddress());

        return userRepository.save(user);
    }

    public List<UserDto.UserResponseDto> getUser() {
        return userRepository.findAll().stream().map(UserDto.UserResponseDto::new).toList();
    }


}
