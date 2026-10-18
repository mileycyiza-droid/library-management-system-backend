
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

import kigali.library.rw.domain.Librarian;
import kigali.library.rw.service.LibrarianService;

@RestController
@RequestMapping("/api/librarians")
public class LibrarianController {

    private final LibrarianService librarianService;

    public LibrarianController(LibrarianService librarianService) {
        this.librarianService = librarianService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Librarian librarian) {
        librarianService.save(librarian);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Librarian saved successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Librarian>> findAll() {
        return ResponseEntity.ok(librarianService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Librarian> librarian = librarianService.findById(id);

        if (librarian.isPresent()) {
            return ResponseEntity.ok(librarian.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The librarian with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody Librarian librarian) {

        Optional<Librarian> updatedLibrarian =
                librarianService.update(id, librarian);

        if (updatedLibrarian.isPresent()) {
            return ResponseEntity.ok("Librarian updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The librarian with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        if (librarianService.delete(id)) {
            return ResponseEntity.ok("Librarian deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The librarian with that id does not exist");
        }
    }
}
