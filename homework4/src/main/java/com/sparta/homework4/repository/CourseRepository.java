package com.sparta.homework4.repository;

import com.sparta.homework4.constant.Category;
import com.sparta.homework4.domain.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    List<CourseEntity> findByTeacherIdOrderByLocalDateTimeDesc(Long teacherId);

    List<CourseEntity> findByCategoryOrderByLocalDateTimeDesc(Category category);

    List<CourseEntity> findAllByOrderByNameDesc();
    List<CourseEntity> findAllByOrderByPriceDesc();
}
