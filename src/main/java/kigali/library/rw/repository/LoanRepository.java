package kigali.library.rw.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import kigali.library.rw.domain.Loan;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
}
