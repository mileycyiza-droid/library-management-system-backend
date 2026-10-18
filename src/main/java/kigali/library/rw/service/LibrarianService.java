
package kigali.library.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import kigali.library.rw.domain.Librarian;
import kigali.library.rw.repository.LibrarianRepository;

@Service
public class LibrarianService {

    private final LibrarianRepository librarianRepository;

    public LibrarianService(LibrarianRepository librarianRepository) {
        this.librarianRepository = librarianRepository;
    }

    public Librarian save(Librarian librarian) {
        return librarianRepository.save(librarian);
    }

    public List<Librarian> findAll() {
        return librarianRepository.findAll();
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

    public boolean delete(UUID id) {
        if (librarianRepository.existsById(id)) {
            librarianRepository.deleteById(id);
            return true;
        }

        return false;
    }
}

