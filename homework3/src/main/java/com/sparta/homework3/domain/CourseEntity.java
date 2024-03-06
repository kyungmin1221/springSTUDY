package com.sparta.homework3.domain;


import com.sparta.homework3.constant.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class CourseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_ID")
    private Long id;

    private String name;

    private int price;

    // 소개
    private String intro;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "TEACHER_ID")
    private TeacherEntity teachers;

    private LocalDateTime localDateTime;
}
