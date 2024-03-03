package com.sparta.homework2.service;


import com.sparta.homework2.domain.BookEntity;
import com.sparta.homework2.dto.BookDto;
import com.sparta.homework2.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;


    // 도서 생성
    @Transactional
    public BookEntity createBook(BookDto.BookRequestDto bookRequestDto) {
        BookEntity book = new BookEntity();
        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setPublisher(bookRequestDto.getPublisher());
        book.setLanguage(bookRequestDto.getLanguage());
        book.setRegisterDate(bookRequestDto.getRegisterDate());

        return bookRepository.save(book);
    }


    // 책 전체 조회
    public List<BookDto.BookResponseDto> getBook() {
        return bookRepository.findAll().stream().map(BookDto.BookResponseDto::new).toList();
    }


    // 책 세부 조회
    public BookDto.BookResponseDto getBookById(Long bookId) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 도서입니다. : " + bookId));

        return convertToDto(book);
    }


    // entity -> dto 변환 메서드
    private BookDto.BookResponseDto convertToDto(BookEntity book) {
        if (book == null) {
            return null;
        }

        BookDto.BookResponseDto bookResponseDto = new BookDto.BookResponseDto();
        bookResponseDto.setId(book.getId());
        bookResponseDto.setTitle(book.getTitle());
        bookResponseDto.setAuthor(book.getAuthor());
        bookResponseDto.setLanguage(book.getLanguage());
        bookResponseDto.setPublisher(book.getPublisher());
        bookResponseDto.setRegisterDate(book.getRegisterDate());

        return bookResponseDto;
    }
}

