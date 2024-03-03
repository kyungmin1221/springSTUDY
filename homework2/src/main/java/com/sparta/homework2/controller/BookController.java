package com.sparta.homework2.controller;


import com.sparta.homework2.domain.BookEntity;
import com.sparta.homework2.dto.BookDto;
import com.sparta.homework2.repository.BookRepository;
import com.sparta.homework2.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/books")
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDto.BookResponseDto> createBook(@RequestBody BookDto.BookRequestDto bookRequestDto) {
        BookEntity book = bookService.createBook(bookRequestDto);
        BookDto.BookResponseDto bookResponseDto = new BookDto.BookResponseDto(book);
        return ResponseEntity.ok(bookResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<BookDto.BookResponseDto>> getBook() {
        List<BookDto.BookResponseDto> books = bookService.getBook();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto.BookResponseDto> getBookById(@PathVariable Long bookId) {
        return ResponseEntity.ok().body(bookService.getBookById(bookId));
    }


}
