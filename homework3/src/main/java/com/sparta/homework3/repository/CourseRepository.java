package com.sparta.homework3.repository;

import com.sparta.homework3.constant.Category;
import com.sparta.homework3.domain.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    List<CourseEntity> findByTeachersIdOrderByLocalDateTimeDesc(Long teacherId);

    List<CourseEntity> findByCategoryOrderByLocalDateTimeDesc(Category category);
}
