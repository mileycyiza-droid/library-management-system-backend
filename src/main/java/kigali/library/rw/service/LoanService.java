
package kigali.library.rw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kigali.library.rw.domain.Loan;
import kigali.library.rw.domain.LoanStatus;
import kigali.library.rw.repository.FineRepository;
import kigali.library.rw.repository.LoanRepository;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final FineRepository fineRepository;

    public LoanService(LoanRepository loanRepository,
                       FineRepository fineRepository) {
        this.loanRepository = loanRepository;
        this.fineRepository = fineRepository;
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

    public Optional<Loan> findById(UUID id) {
        return loanRepository.findById(id);
    }

    public String update(UUID id, Loan loan) {
        Optional<Loan> optionalLoan = loanRepository.findById(id);

        if (optionalLoan.isEmpty()) {
            return "The loan with that id does not exist";
        }

        Loan existingLoan = optionalLoan.get();
        existingLoan.setLoanDate(loan.getLoanDate());
        existingLoan.setBookTitle(loan.getBookTitle());
        existingLoan.setMember(loan.getMember());
        existingLoan.setLibrarian(loan.getLibrarian());

        loanRepository.save(existingLoan);

        return "Loan updated successfully";
    }

    public String delete(UUID id) {
        Optional<Loan> optionalLoan = loanRepository.findById(id);

        if (optionalLoan.isEmpty()) {
            return "The loan with that id does not exist";
        }

        if (fineRepository.existsByLoan_Id(id)) {
            return "That loan has fines, delete them first";
        }

        loanRepository.deleteById(id);

        return "Loan deleted successfully";
    }

    public List<Loan> findByStatus(LoanStatus status) {
        return loanRepository.findByStatusOrderByLoanDateAsc(status);
    }

    public List<Loan> findBetweenDates(LocalDate start, LocalDate end) {
        return loanRepository.findByLoanDateBetweenOrderByLoanDateAsc(
                start, end);
    }

    public String updateStatus(UUID id, LoanStatus status) {
        Optional<Loan> optionalLoan = loanRepository.findById(id);

        if (optionalLoan.isEmpty()) {
            return "The loan with that id does not exist";
        }

        Loan loan = optionalLoan.get();
        LoanStatus currentStatus = loan.getStatus();

        if (currentStatus == LoanStatus.RETURNED
                || currentStatus == LoanStatus.CANCELLED) {
            return "Cannot change status from " + currentStatus + " to " + status;
        }

        if (currentStatus == LoanStatus.REQUESTED
                && status != LoanStatus.ISSUED
                && status != LoanStatus.CANCELLED) {
            return "Cannot change status from " + currentStatus + " to " + status;
        }

        if (currentStatus == LoanStatus.ISSUED
                && status != LoanStatus.RETURNED
                && status != LoanStatus.CANCELLED) {
            return "Cannot change status from " + currentStatus + " to " + status;
        }

        loan.setStatus(status);
        loanRepository.save(loan);

        return "Loan status updated successfully";
    }

    public List<Object[]> countLoansByStatus() {
        return loanRepository.countLoansByStatus();
    }

    @Transactional
    public String cancelLoansForLibrarianOnDate(
            UUID librarianId, LocalDate date) {

        int count = loanRepository.cancelLoansForLibrarianOnDate(
                librarianId,
                date,
                LoanStatus.CANCELLED,
                LoanStatus.RETURNED);

        return count + " loans cancelled";
    }
}
