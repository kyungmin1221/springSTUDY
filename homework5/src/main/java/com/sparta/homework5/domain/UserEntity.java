package com.sparta.homework5.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.homework5.constant.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;


    /*
    <@JsonIgnore>
    Jackson 라이브러리를 사용하여 JSON으로 직렬화할 때,
    특정 필드를 무시하도록 설정할 수 있다.
    @JsonIgnore 어노테이션을 사용하여 basketList 필드를 JSON 직렬화 대상에서 제외
     */
//    @JsonIgnore     // could not initialize proxy - no Session 오류해결
//    @OneToMany(mappedBy = "user")
//    private List<FolderEntity> basketList = new ArrayList<>();

    @Builder
    public UserEntity(String email, String password, String gender,String phoneNumber,String address, Role role) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }
}
