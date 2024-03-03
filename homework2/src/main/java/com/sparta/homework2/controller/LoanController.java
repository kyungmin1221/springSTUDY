package com.sparta.homework2.controller;


import com.sparta.homework2.domain.LoanEntity;
import com.sparta.homework2.dto.LoanDto;
import com.sparta.homework2.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/books/loan")
@RestController
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    // 도서 대출 생성
    @PostMapping
    public ResponseEntity<LoanDto.LoanResponseDto> createLoan(@RequestBody LoanDto.LoanRequestDto loanRequestDto) {
        LoanEntity loan = loanService.createLoan(loanRequestDto);
        LoanDto.LoanResponseDto loanResponseDto = new LoanDto.LoanResponseDto(loan);

        return ResponseEntity.ok(loanResponseDto);
    }

    // 전체 도서 대출 내역 조회
    @GetMapping("/loanBooks")
    public ResponseEntity<List<LoanDto.LoanResponseDto>> getAllLoan() {
        List<LoanDto.LoanResponseDto> loanBooks = loanService.getAllLoan();

        return ResponseEntity.ok(loanBooks);
    }



    // 회원의 도서 대출 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<LoanDto.LoanResponseDto>> getLoanById(@PathVariable Long userId) {
        return ResponseEntity.ok().body(loanService.getLoanById(userId));
    }


    // 도서 반납
    @PatchMapping("/{loanId}")
    public ResponseEntity<LoanDto.LoanResponseDto> returnLoan(@PathVariable Long loanId, @RequestBody LoanDto.LoanRequestDto loanRequestDto) {
        return ResponseEntity.ok().body(loanService.returnLoan(loanId, loanRequestDto));
    }

}
