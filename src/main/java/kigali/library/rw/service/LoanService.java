
package kigali.library.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import kigali.library.rw.domain.Loan;
import kigali.library.rw.repository.LoanRepository;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan save(Loan loan) {
        return loanRepository.save(loan);
    }

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public Optional<Loan> findById(UUID id) {
        return loanRepository.findById(id);
    }

    public Optional<Loan> update(UUID id, Loan loan) {
        return loanRepository.findById(id).map(existingLoan -> {
            existingLoan.setLoanDate(loan.getLoanDate());
            existingLoan.setStatus(loan.getStatus());
            existingLoan.setBookTitle(loan.getBookTitle());
            existingLoan.setMember(loan.getMember());
            existingLoan.setLibrarian(loan.getLibrarian());

            return loanRepository.save(existingLoan);
        });
    }

    public boolean delete(UUID id) {
        if (loanRepository.existsById(id)) {
            loanRepository.deleteById(id);
            return true;
        }

        return false;
    }
}

