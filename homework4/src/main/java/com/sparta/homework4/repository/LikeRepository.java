package com.sparta.homework4.repository;

import com.sparta.homework4.domain.CourseEntity;
import com.sparta.homework4.domain.LikeEntity;
import com.sparta.homework4.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    // 좋아요를 위한 회원과 강의 유무 확인
    Optional<LikeEntity> findByUserIdAndCourseId(Long userId, Long courseId);
}