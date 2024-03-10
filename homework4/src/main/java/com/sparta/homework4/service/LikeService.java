package com.sparta.homework4.service;

import com.sparta.homework4.domain.CourseEntity;
import com.sparta.homework4.domain.LikeEntity;
import com.sparta.homework4.domain.UserEntity;
import com.sparta.homework4.dto.LikeDto;
import com.sparta.homework4.exception.CustomException;
import com.sparta.homework4.exception.ErrorCode;
import com.sparta.homework4.repository.CourseRepository;
import com.sparta.homework4.repository.LikeRepository;
import com.sparta.homework4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    // 중요 요구사항 //
    // 이미 해당 강의에 좋아요를 한 상태라면 좋아요가 취소됩니다.
    // 선택한 강의를 조회할 때 해당 강의의 좋아요 수 를 함께 조회할 수 있습니다.
    private final CourseRepository courseRepository;
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final CourseService courseService;

    // 좋아요 클릭
    // 반환값 변경
    @Transactional
    public LikeDto.LikeResponseDto toggleLike(Long userId, Long courseId) {
        UserEntity user = userService.findUserById(userId);
        CourseEntity course = courseService.findCourseById(courseId);

        Optional<LikeEntity> like = likeRepository.findByUserIdAndCourseId(userId, courseId);

        if (like.isPresent()) {
            return removeLike(like.get(), course);

        } else {
            return addLike(user,course);
        }
    }

    // 좋아요를 클릭했을때 이미 존재한다면 취소
    public LikeDto.LikeResponseDto removeLike(LikeEntity like, CourseEntity course) {
        likeRepository.delete(like);
        course.setLikeCount(course.getLikeCount() - 1);
        courseRepository.save(course);
        return new LikeDto.LikeResponseDto(false,"좋아요가 취소");
    }

    // 좋아요 클릭
    public LikeDto.LikeResponseDto addLike(UserEntity user, CourseEntity course) {
        LikeEntity like = new LikeEntity();
        like.setUser(user);
        like.setCourse(course);
        likeRepository.save(like);
        course.setLikeCount(course.getLikeCount() + 1);
        courseRepository.save(course);
        return new LikeDto.LikeResponseDto(true,"좋아요를 눌렀습니다.");
    }

}




