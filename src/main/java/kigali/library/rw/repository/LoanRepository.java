
package kigali.library.rw.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import kigali.library.rw.domain.Loan;
import kigali.library.rw.domain.LoanStatus;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    List<Loan> findByStatusOrderByLoanDateAsc(LoanStatus status);

    List<Loan> findByLoanDateBetweenOrderByLoanDateAsc(
            LocalDate start,
            LocalDate end);
}
