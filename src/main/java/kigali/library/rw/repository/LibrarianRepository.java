
package kigali.library.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kigali.library.rw.domain.Librarian;

public interface LibrarianRepository extends JpaRepository<Librarian, UUID> {

    @Query("SELECT l FROM Librarian l JOIN l.sections s WHERE LOWER(s.name) = LOWER(:name)")
    List<Librarian> findBySectionName(@Param("name") String name);
}
