package com.sparta.homework2.dto;

import com.sparta.homework2.domain.LoanEntity;
import lombok.*;

import java.time.LocalDateTime;

public class LoanDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class LoanResponseDto {

        private Long id;

        private Long userId;

        private Long bookId;

        private LocalDateTime loanDate;

        private LocalDateTime returnDate;

        private boolean isReturned;

        public LoanResponseDto(LoanEntity loan) {
            this.id = loan.getId();
            this.userId = loan.getUserId();
            this.bookId = loan.getBookId();
            this.loanDate = loan.getLoanDate();
            this.returnDate = loan.getReturnDate();
            this.isReturned = loan.isReturned();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class LoanRequestDto {

        private Long userId;

        private Long bookId;

        private LocalDateTime loanDate;

        private LocalDateTime returnDate;

        private boolean isReturned;

    }

//    @Getter
//    @Setter
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Builder
//    public static class LoanPatchDto {
//
//        private Long id;
//
//        private LocalDateTime loanDate;
//
//        private LocalDateTime returnDate;
//
//        private boolean isReturned;
//
//    }
}
