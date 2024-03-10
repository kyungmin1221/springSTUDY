package com.sparta.homework4.config.security;


import com.sparta.homework4.domain.UserEntity;
import com.sparta.homework4.exception.CustomException;
import com.sparta.homework4.exception.ErrorCode;
import com.sparta.homework4.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 사용자 정보를 조회하고 검증한 후 userDatail을 반환
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // 클라이언트가 인증관리자에게 정보를 요청(여기선 이메일) -> 반환
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
//                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일을 가진 사용자를 찾을 수 없습니다 :  " + email));
        return new UserDetailsImpl(user);
    }
}

