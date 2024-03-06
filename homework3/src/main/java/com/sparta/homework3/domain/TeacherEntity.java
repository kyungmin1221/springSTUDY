package com.sparta.homework3.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor
public class TeacherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INSTRUCTOR_ID")
    private Long id;

    private String name;

    private int career;

    private String company;

    private String tel;

    private String introdution;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

}
