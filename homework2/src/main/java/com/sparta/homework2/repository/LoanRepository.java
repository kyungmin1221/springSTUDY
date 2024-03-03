package com.sparta.homework2.repository;

import com.sparta.homework2.domain.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    Optional<LoanEntity> findByBookIdAndIsReturned(Long bookId, boolean isReturned);

    List<LoanEntity> findAllByOrderByLoanDateAsc();
    // 대출내역
    List<LoanEntity> findByUserIdAndIsReturnedFalseOrderByLoanDateAsc(Long userId);
}
