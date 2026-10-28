
package kigali.library.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kigali.library.rw.domain.Section;

public interface SectionRepository extends JpaRepository<Section, UUID> {

    @Query("SELECT s FROM Section s WHERE s.librarians IS EMPTY")
    List<Section> findUnusedSections();
}
