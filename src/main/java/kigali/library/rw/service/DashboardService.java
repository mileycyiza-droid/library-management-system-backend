package kigali.library.rw.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import kigali.library.rw.domain.LoanStatus;
import kigali.library.rw.repository.DeskRepository;
import kigali.library.rw.repository.FineRepository;
import kigali.library.rw.repository.LibrarianRepository;
import kigali.library.rw.repository.LoanRepository;
import kigali.library.rw.repository.MemberRepository;

@Service
public class DashboardService {

    private final MemberRepository memberRepository;
    private final LibrarianRepository librarianRepository;
    private final DeskRepository deskRepository;
    private final LoanRepository loanRepository;
    private final FineRepository fineRepository;

    public DashboardService(
            MemberRepository memberRepository,
            LibrarianRepository librarianRepository,
            DeskRepository deskRepository,
            LoanRepository loanRepository,
            FineRepository fineRepository) {

        this.memberRepository = memberRepository;
        this.librarianRepository = librarianRepository;
        this.deskRepository = deskRepository;
        this.loanRepository = loanRepository;
        this.fineRepository = fineRepository;
    }

    public Map<String, Long> getSummary() {

        Map<String, Long> summary = new LinkedHashMap<>();

        summary.put("members", memberRepository.count());
        summary.put("librarians", librarianRepository.count());
        summary.put("freeDesks", (long) deskRepository.findFreeDesks().size());
        summary.put("loansToday", loanRepository.countByLoanDate(LocalDate.now()));
        summary.put("requested", loanRepository.countByStatus(LoanStatus.REQUESTED));
        summary.put("fines", fineRepository.count());

        return summary;
    }
}
