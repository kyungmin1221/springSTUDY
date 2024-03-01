package com.sparta.homework1.dto;

import com.sparta.homework1.domain.UserEntity;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private Long id;
    private String title;
    private String name;
    private String contents;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDate;

    public UserResponseDto(UserEntity user) {
        this.id = user.getId();
        this.title = user.getTitle();
        this.name = user.getName();
        this.contents = user.getContents();
        this.orderDate = user.getOrderDate();
    }
}
