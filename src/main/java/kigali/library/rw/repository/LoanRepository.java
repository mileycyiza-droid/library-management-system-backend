
package kigali.library.rw.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kigali.library.rw.domain.Loan;
import kigali.library.rw.domain.LoanStatus;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    List<Loan> findByStatusOrderByLoanDateAsc(LoanStatus status);

    List<Loan> findByLoanDateBetweenOrderByLoanDateAsc(
            LocalDate start,
            LocalDate end);

    boolean existsByLibrarian_IdAndLoanDateAndStatusNot(
            UUID librarianId,
            LocalDate loanDate,
            LoanStatus status);

    @Query("SELECT l.status, COUNT(l) FROM Loan l GROUP BY l.status")
    List<Object[]> countLoansByStatus();

    @Modifying
    @Query("UPDATE Loan l SET l.status = :status " +
           "WHERE l.librarian.id = :librarianId " +
           "AND l.loanDate = :date " +
           "AND l.status <> :returnedStatus")
    int cancelLoansForLibrarianOnDate(
            @Param("librarianId") UUID librarianId,
            @Param("date") LocalDate date,
            @Param("status") LoanStatus status,
            @Param("returnedStatus") LoanStatus returnedStatus);
}
