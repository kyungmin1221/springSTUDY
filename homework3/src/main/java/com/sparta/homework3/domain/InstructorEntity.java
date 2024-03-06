package com.sparta.homework3.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@NoArgsConstructor
public class InstructorEntity {

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
