package com.sparta.homework1.domain;


import com.sparta.homework1.dto.UserRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class UserEntity {

    private Long id;
    private String title;
    private String name;
    private String password;
    private String contents;
    private LocalDateTime orderDate;

    public UserEntity(UserRequestDto userRequestDto) {

        this.title = userRequestDto.getTitle();
        this.name = userRequestDto.getName();
        this.password = userRequestDto.getPassword();
        this.contents = userRequestDto.getContents();
        this.orderDate = userRequestDto.getOrderDate();
    }

}
