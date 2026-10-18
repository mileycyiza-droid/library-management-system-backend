
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

import kigali.library.rw.domain.Desk;
import kigali.library.rw.service.DeskService;

@RestController
@RequestMapping("/api/desks")
public class DeskController {

    private final DeskService deskService;

    public DeskController(DeskService deskService) {
        this.deskService = deskService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Desk desk) {
        deskService.save(desk);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Desk saved successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Desk>> findAll() {
        return ResponseEntity.ok(deskService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Desk> desk = deskService.findById(id);

        if (desk.isPresent()) {
            return ResponseEntity.ok(desk.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The desk with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody Desk desk) {

        Optional<Desk> updatedDesk = deskService.update(id, desk);

        if (updatedDesk.isPresent()) {
            return ResponseEntity.ok("Desk updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The desk with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        if (deskService.delete(id)) {
            return ResponseEntity.ok("Desk deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The desk with that id does not exist");
        }
    }
}
