package com.sparta.homework2.domain;

import com.sparta.homework2.dto.UserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class UserEntity extends TimeStamp{

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(nullable = false)
    private String gender;

    @Column(unique = true)
    private String securityNumber;

    @Column(unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

}
