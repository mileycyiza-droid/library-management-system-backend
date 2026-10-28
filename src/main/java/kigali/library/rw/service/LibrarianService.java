package kigali.library.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import kigali.library.rw.domain.Desk;
import kigali.library.rw.domain.Librarian;
import kigali.library.rw.domain.Section;
import kigali.library.rw.repository.DeskRepository;
import kigali.library.rw.repository.LibrarianRepository;
import kigali.library.rw.repository.SectionRepository;

@Service
public class LibrarianService {

    private final LibrarianRepository librarianRepository;
    private final SectionRepository sectionRepository;
    private final DeskRepository deskRepository;

    public LibrarianService(
            LibrarianRepository librarianRepository,
            SectionRepository sectionRepository,
            DeskRepository deskRepository) {
        this.librarianRepository = librarianRepository;
        this.sectionRepository = sectionRepository;
        this.deskRepository = deskRepository;
    }

    public Librarian save(Librarian librarian) {
        return librarianRepository.save(librarian);
    }

    public List<Librarian> findAll() {
        return librarianRepository.findAll();
    }

    public List<Librarian> findBySectionName(String name) {
        return librarianRepository.findBySectionName(name);
    }

    public Optional<Librarian> findById(UUID id) {
        return librarianRepository.findById(id);
    }

    public Optional<Librarian> update(UUID id, Librarian librarian) {
        return librarianRepository.findById(id).map(existingLibrarian -> {
            existingLibrarian.setFirstName(librarian.getFirstName());
            existingLibrarian.setLastName(librarian.getLastName());
            existingLibrarian.setEmail(librarian.getEmail());
            existingLibrarian.setDesk(librarian.getDesk());
            existingLibrarian.setSections(librarian.getSections());

            return librarianRepository.save(existingLibrarian);
        });
    }

    public String addSection(UUID librarianId, UUID sectionId) {
        Optional<Librarian> optionalLibrarian =
                librarianRepository.findById(librarianId);

        if (optionalLibrarian.isEmpty()) {
            return "The librarian with that id does not exist";
        }

        Optional<Section> optionalSection =
                sectionRepository.findById(sectionId);

        if (optionalSection.isEmpty()) {
            return "That section does not exist";
        }

        Librarian librarian = optionalLibrarian.get();
        Section section = optionalSection.get();

        if (librarian.getSections().contains(section)) {
            return "The librarian already has that section";
        }

        librarian.getSections().add(section);
        librarianRepository.save(librarian);

        return "Section added to the librarian";
    }

    public String changeDesk(UUID librarianId, int deskNumber) {
        Optional<Librarian> optionalLibrarian =
                librarianRepository.findById(librarianId);

        if (optionalLibrarian.isEmpty()) {
            return "The librarian with that id does not exist";
        }

        Optional<Desk> optionalDesk =
                deskRepository.findByDeskNumber(deskNumber);

        if (optionalDesk.isEmpty()) {
            return "The desk with that deskNumber does not exist";
        }

        Librarian librarian = optionalLibrarian.get();
        Desk desk = optionalDesk.get();

        if (librarianRepository.existsByDesk(desk)
                && !desk.equals(librarian.getDesk())) {
            return "That desk is taken by another librarian";
        }

        librarian.setDesk(desk);
        librarianRepository.save(librarian);

        return "Desk changed successfully";
    }

    public boolean delete(UUID id) {
        if (librarianRepository.existsById(id)) {
            librarianRepository.deleteById(id);
            return true;
        }

        return false;
    }
}