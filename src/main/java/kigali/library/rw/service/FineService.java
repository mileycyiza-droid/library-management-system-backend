
package kigali.library.rw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import kigali.library.rw.domain.Fine;
import kigali.library.rw.domain.Loan;
import kigali.library.rw.domain.LoanStatus;
import kigali.library.rw.repository.FineRepository;
import kigali.library.rw.repository.LoanRepository;

@Service
public class FineService {

    private final FineRepository fineRepository;
    private final LoanRepository loanRepository;

    public FineService(FineRepository fineRepository,
                       LoanRepository loanRepository) {
        this.fineRepository = fineRepository;
        this.loanRepository = loanRepository;
    }

    public String save(Fine fine, UUID loanId) {
        Loan loan = loanRepository.findById(loanId).orElse(null);

        if (loan == null) {
            return "The loan with that id does not exist";
        }

        if (loan.getStatus() != LoanStatus.RETURNED) {
            return "Fines are only for RETURNED loans";
        }

        fine.setId(null);
        fine.setLoan(loan);
        fine.setIssuedDate(LocalDate.now());

        fineRepository.save(fine);

        return "Fine saved successfully";
    }

    public List<Fine> findByMemberNumber(String memberNumber) {
        return fineRepository.findFinesByMemberNumber(memberNumber);
    }

    public String delete(UUID id) {
        Fine fine = fineRepository.findById(id).orElse(null);

        if (fine == null) {
            return "The fine with that id does not exist";
        }

        fineRepository.delete(fine);

        return "Fine deleted successfully";
    }

    public boolean loanHasFines(UUID loanId) {
        return fineRepository.existsByLoan_Id(loanId);
    }
}
