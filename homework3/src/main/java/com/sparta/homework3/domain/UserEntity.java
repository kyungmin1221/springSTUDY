package com.sparta.homework3.domain;

import com.sparta.homework3.constant.Department;
import com.sparta.homework3.constant.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class UserEntity {   // 관리자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Column(name = "USER_ID",unique = true)
    private String email;

    // @Size(min = 8, max = 15)
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Department department;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<InstructorEntity> instructors = new ArrayList<>();

    public UserEntity(String email, String password, Department department, Role role) {
        this.email = email;
        this.password = password;
        this.department = department;
        this.role = role;
    }

}
