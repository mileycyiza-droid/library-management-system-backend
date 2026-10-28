package kigali.library.rw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import kigali.library.rw.domain.Loan;
import kigali.library.rw.domain.LoanStatus;
import kigali.library.rw.repository.LoanRepository;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public String save(Loan loan) {
        if (loan.getLoanDate().isBefore(LocalDate.now())) {
            return "Loan date cannot be in the past";
        }

        if (loanRepository.existsByLibrarian_IdAndLoanDateAndStatusNot(
                loan.getLibrarian().getId(),
                loan.getLoanDate(),
                LoanStatus.CANCELLED)) {
            return "Librarian already has a loan on that date";
        }

        loan.setStatus(LoanStatus.REQUESTED);
        loanRepository.save(loan);

        return "Loan saved successfully";
    }

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public List<Loan> findByStatus(LoanStatus status) {
        return loanRepository.findByStatusOrderByLoanDateAsc(status);
    }

    public List<Loan> findBetweenDates(LocalDate start, LocalDate end) {
        return loanRepository.findByLoanDateBetweenOrderByLoanDateAsc(
                start, end);
    }

    public Optional<Loan> findById(UUID id) {
        return loanRepository.findById(id);
    }

    public Optional<Loan> update(UUID id, Loan loan) {
        return loanRepository.findById(id).map(existingLoan -> {
            existingLoan.setLoanDate(loan.getLoanDate());
            existingLoan.setBookTitle(loan.getBookTitle());
            existingLoan.setMember(loan.getMember());
            existingLoan.setLibrarian(loan.getLibrarian());

            return loanRepository.save(existingLoan);
        });
    }

    public String updateStatus(UUID id, LoanStatus newStatus) {
        Optional<Loan> optionalLoan = loanRepository.findById(id);

        if (optionalLoan.isEmpty()) {
            return "The loan with that id does not exist";
        }

        Loan loan = optionalLoan.get();
        LoanStatus currentStatus = loan.getStatus();

        boolean validTransition =
                (currentStatus == LoanStatus.REQUESTED
                        && (newStatus == LoanStatus.ISSUED
                        || newStatus == LoanStatus.CANCELLED))
                || (currentStatus == LoanStatus.ISSUED
                        && (newStatus == LoanStatus.RETURNED
                        || newStatus == LoanStatus.CANCELLED));

        if (!validTransition) {
            return "Cannot change status from "
                    + currentStatus + " to " + newStatus;
        }

        loan.setStatus(newStatus);
        loanRepository.save(loan);

        return "Loan status updated successfully";
    }

    public boolean delete(UUID id) {
        if (loanRepository.existsById(id)) {
            loanRepository.deleteById(id);
            return true;
        }

        return false;
    }
}