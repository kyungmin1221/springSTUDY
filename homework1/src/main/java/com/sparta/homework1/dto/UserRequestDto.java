package com.sparta.homework1.dto;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class UserRequestDto {

    private Long id;
    private String title;
    private String name;
    private String password;
    private String contents;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDate;

}
