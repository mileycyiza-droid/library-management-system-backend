
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

import kigali.library.rw.domain.Section;
import kigali.library.rw.service.SectionService;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Section section) {
        sectionService.save(section);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Section saved successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Section>> findAll() {
        return ResponseEntity.ok(sectionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Section> section = sectionService.findById(id);

        if (section.isPresent()) {
            return ResponseEntity.ok(section.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The section with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody Section section) {

        Optional<Section> updatedSection = sectionService.update(id, section);

        if (updatedSection.isPresent()) {
            return ResponseEntity.ok("Section updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The section with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        if (sectionService.delete(id)) {
            return ResponseEntity.ok("Section deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The section with that id does not exist");
        }
    }
}

