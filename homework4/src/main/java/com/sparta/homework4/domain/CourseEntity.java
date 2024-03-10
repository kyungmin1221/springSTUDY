package com.sparta.homework4.domain;


import com.sparta.homework4.constant.Category;
import com.sparta.homework4.dto.CourseDto;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
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

    @Column(nullable = false, unique = true)
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
    private TeacherEntity teacher;

    private LocalDateTime localDateTime;

    @Column(nullable = false)
    private int likeCount;

    @OneToMany(mappedBy = "course",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<LikeEntity> likes = new ArrayList<>();


    public CourseEntity(CourseDto.CourseRequestDto requestDto) {
        this.name = requestDto.getName();
        this.price = requestDto.getPrice();
        this.intro = requestDto.getIntro();
        this.category = requestDto.getCategory();
    }
}
