package kigali.library.rw.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kigali.library.rw.domain.Desk;

public interface DeskRepository extends JpaRepository<Desk, UUID> {

    Optional<Desk> findByDeskNumber(int deskNumber);

    @Query("SELECT d FROM Desk d WHERE NOT EXISTS (SELECT l FROM Librarian l WHERE l.desk = d) ORDER BY d.deskNumber ASC")
    List<Desk> findFreeDesks();
}