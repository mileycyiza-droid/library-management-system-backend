
package kigali.library.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kigali.library.rw.domain.Fine;
import kigali.library.rw.service.FineService;

@RestController
@RequestMapping("/api/fines")
public class FineController {

    private final FineService fineService;

    public FineController(FineService fineService) {
        this.fineService = fineService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(
            @RequestBody Fine fine,
            @RequestParam UUID loanId) {

        String message = fineService.save(fine, loanId);

        if (message.equals("The loan with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else if (message.equals("Fines are only for RETURNED loans")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
    }

    @GetMapping("/member/{memberNumber}")
    public ResponseEntity<List<Fine>> findByMemberNumber(
            @PathVariable String memberNumber) {

        return ResponseEntity.ok(
                fineService.findByMemberNumber(memberNumber));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = fineService.delete(id);

        if (message.equals("The fine with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }
}
