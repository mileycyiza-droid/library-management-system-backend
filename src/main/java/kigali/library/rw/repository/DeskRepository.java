package kigali.library.rw.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import kigali.library.rw.domain.Desk;

public interface DeskRepository extends JpaRepository<Desk, UUID> {
}
