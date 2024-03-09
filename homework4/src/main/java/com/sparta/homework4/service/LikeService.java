package com.sparta.homework4.service;

import com.sparta.homework4.domain.CourseEntity;
import com.sparta.homework4.domain.LikeEntity;
import com.sparta.homework4.domain.UserEntity;
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
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final LikeRepository likeRepository;

    // 좋아요 클릭

    // 반환값 변경
    @Transactional
    public String toggleLike(Long userId, Long courseId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomException(ErrorCode.COURSE_NOT_FOUND));

        Optional<LikeEntity> addlike = likeRepository.findByUserIdAndCourseId(userId, courseId);

//        LikeEntity like = addlike.orElse(null);
//        if(!addlike.isPresent()) {
//            like =  new LikeEntity();
//            like.setUser(user);
//            like.setCourse(course);
//
//        }

        // 이미 '좋아요'를 등록한 경우, '좋아요' 취소
        if (addlike.isPresent()) {
            likeRepository.delete(addlike.get());
            course.setLikeCount(course.getLikeCount() - 1); // '좋아요' 개수 감소
            courseRepository.save(course);
            return "해당 강의에 좋아요가 취소되었습니다.";

        } else {
            // '좋아요'를 등록하지 않은 경우, '좋아요' 추가
            LikeEntity like = new LikeEntity();
            like.setUser(user);
            like.setCourse(course);
            likeRepository.save(like);
            course.setLikeCount(course.getLikeCount() + 1); // '좋아요' 개수 증가
            courseRepository.save(course);
            return "해당 강의에 좋아요가 등록되었습니다.";
        }

    }

}




