
package kigali.library.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kigali.library.rw.domain.Fine;

public interface FineRepository extends JpaRepository<Fine, UUID> {

    @Query("SELECT f FROM Fine f " +
           "WHERE f.loan.member.memberNumber = :memberNumber " +
           "ORDER BY f.issuedDate DESC")
    List<Fine> findFinesByMemberNumber(
            @Param("memberNumber") String memberNumber);

    boolean existsByLoan_Id(UUID loanId);
}
