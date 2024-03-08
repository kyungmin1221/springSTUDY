package com.sparta.homework4.domain;


import com.sparta.homework4.constant.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class CourseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    // 소개
    @Column(nullable = false)
    private String intro;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teachers;

   @OneToMany(mappedBy = "courses")
   private List<LikeEntity> likes = new ArrayList<>();

   @OneToMany(mappedBy = "courses")
   private List<CommentEntity> comments = new ArrayList<>();



    private LocalDateTime localDateTime;
}
