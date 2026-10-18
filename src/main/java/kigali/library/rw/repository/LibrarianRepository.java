package kigali.library.rw.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import kigali.library.rw.domain.Librarian;

public interface LibrarianRepository extends JpaRepository<Librarian, UUID> {
}