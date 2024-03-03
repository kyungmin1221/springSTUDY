package com.sparta.homework2.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "LOAN_BOOK")
public class LoanEntity extends TimeStamp{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long id;

    private Long userId;

    private Long bookId;

    // 도서 반납 상태
    private boolean isReturned;

    // 도서 대출일
    private LocalDateTime loanDate;

    // 도서 반납일
    private LocalDateTime returnDate;

}
