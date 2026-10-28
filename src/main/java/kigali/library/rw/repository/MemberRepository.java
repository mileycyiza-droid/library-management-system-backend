package kigali.library.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kigali.library.rw.domain.Member;

public interface MemberRepository extends JpaRepository<Member, UUID> {

    // Part B: Find members by last name, ignoring case, ordered by first name
    List<Member> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    // Part C4: Find unique members who have loans with a specific librarian
    @Query("SELECT DISTINCT lo.member FROM Loan lo WHERE lo.librarian.id = :librarianId")
    List<Member> findMembersByLibrarianId(@Param("librarianId") UUID librarianId);
}