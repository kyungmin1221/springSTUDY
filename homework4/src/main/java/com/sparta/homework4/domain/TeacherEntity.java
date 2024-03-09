package com.sparta.homework4.domain;

import com.sparta.homework4.dto.TeacherDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@NoArgsConstructor
public class TeacherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int career;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String tel;

    @Column(nullable = false)
    private String introdution;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseEntity> courses = new ArrayList<>();

    public TeacherEntity(TeacherDto.TeacherRequestDto requestDto) {
        this.name = requestDto.getName();
        this.career = requestDto.getCareer();
        this.company = requestDto.getCompany();
        this.tel = requestDto.getTel();
        this.introdution = requestDto.getIntrodution();
    }
}
