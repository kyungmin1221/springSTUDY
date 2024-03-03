package com.sparta.homework2.dto;

import com.sparta.homework2.domain.BookEntity;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

public class BookDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BookResponseDto {

        private Long id;

        private String title;

        private String author;

        private String language;

        private String publisher;

        private LocalDateTime registerDate;

        public BookResponseDto(BookEntity book) {
            this.id = book.getId();
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.language = book.getLanguage();
            this.publisher = book.getPublisher();
            this.registerDate = book.getRegisterDate();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BookRequestDto {

        private String title;

        private String author;

        private String language;

        private String publisher;

        private LocalDateTime registerDate;

    }
}
