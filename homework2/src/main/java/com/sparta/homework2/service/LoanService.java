package com.sparta.homework2.service;


import com.sparta.homework2.domain.LoanEntity;
import com.sparta.homework2.dto.LoanDto;
import com.sparta.homework2.repository.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;


    // 도서 대출
    public LoanEntity createLoan(LoanDto.LoanRequestDto loanRequestDto) {

        // 반납하지 않은 책이 있다면 대출이 불가능
        boolean isAlreadyLoaned = loanRepository.findByBookIdAndIsReturned(loanRequestDto.getBookId(), false).isPresent();
        if(isAlreadyLoaned) {
            throw new IllegalStateException("이미 대출 중인 도서입니다.");
        }
        LoanEntity loan = new LoanEntity();
        loan.setUserId(loanRequestDto.getUserId());
        loan.setBookId(loanRequestDto.getBookId());
        loan.setReturnDate(loanRequestDto.getReturnDate());
        loan.setLoanDate(loanRequestDto.getLoanDate());

        return loanRepository.save(loan);
    }

    // 전체 대출 내역 조회
    public List<LoanDto.LoanResponseDto> getAllLoan() {
        List<LoanEntity> currentLoan = loanRepository.findAllByOrderByLoanDateAsc();
        return currentLoan.stream()     // 스트림으로 저장하면 저장된 요소들을 하나씩 참조가 가능함
                .map(this::convertToLoanDto)    // convertToLoanDto 를 이용하여 LoanDto.LoanResponseDto 객체로 변환함
                .collect(Collectors.toList());  // 매핑된 스트림 객체들을 리스트로 수집, Collectors.toList() 는 수집된 결과를 리스트 형태로 반환해준다
    }

    // 회원의 대출 조회(반납된 내역은 제외)
    public List<LoanDto.LoanResponseDto> getLoanById(Long userId) {

        List<LoanEntity> currentLoan = loanRepository.findByUserIdAndIsReturnedFalseOrderByLoanDateAsc(userId);
        return currentLoan.stream()     // 스트림으로 저장하면 저장된 요소들을 하나씩 참조가 가능함
                .map(this::convertToLoanDto)    // convertToLoanDto 를 이용하여 LoanDto.LoanResponseDto 객체로 변환함
                .collect(Collectors.toList());  // 매핑된 스트림 객체들을 리스트로 수집, Collectors.toList() 는 수집된 결과를 리스트 형태로 반환해준다
    }

    // 도서 반납 가능
    @Transactional
    public LoanDto.LoanResponseDto returnLoan(Long loanId, LoanDto.LoanRequestDto loanRequestDto) {
        // 대출 기록 조회
        LoanEntity loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("해당 대출 기록을 찾을 수 없습니다: " + loanId));

        if (loan.isReturned()) {
            throw new IllegalStateException("이미 반납 처리된 대출입니다.");
        }

        // 반납 상태 및 반납일 업데이트
        loan.setReturned(true);
        loan.setReturnDate(LocalDateTime.now()); // 현재 시각을 반납일로 설정

        // 변경된 대출 기록 저장 및 DTO 변환
        LoanEntity savedLoan = loanRepository.save(loan);
        return convertToLoanDto(savedLoan);
    }


    // entity -> dto
    public LoanDto.LoanResponseDto convertToLoanDto(LoanEntity loan) {
        if(loan == null) {
            return null;
        }

        LoanDto.LoanResponseDto loanResponseDto = new LoanDto.LoanResponseDto();
        loanResponseDto.setId(loan.getId());
        loanResponseDto.setUserId(loan.getUserId());
        loanResponseDto.setBookId(loan.getBookId());
        loanResponseDto.setReturned(loan.isReturned());
        loanResponseDto.setLoanDate(loan.getLoanDate());
        loanResponseDto.setReturnDate(loan.getReturnDate());

        return loanResponseDto;
    }
}
