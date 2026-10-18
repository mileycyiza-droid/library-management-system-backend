
package kigali.library.rw.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kigali.library.rw.domain.Loan;
import kigali.library.rw.service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Loan loan) {
        loanService.save(loan);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Loan saved successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Loan>> findAll() {
        return ResponseEntity.ok(loanService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Loan> loan = loanService.findById(id);

        if (loan.isPresent()) {
            return ResponseEntity.ok(loan.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The loan with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody Loan loan) {

        Optional<Loan> updatedLoan = loanService.update(id, loan);

        if (updatedLoan.isPresent()) {
            return ResponseEntity.ok("Loan updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The loan with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        if (loanService.delete(id)) {
            return ResponseEntity.ok("Loan deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The loan with that id does not exist");
        }
    }
}
