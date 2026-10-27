
package kigali.library.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import kigali.library.rw.domain.Member;

public interface MemberRepository extends JpaRepository<Member, UUID> {

    List<Member> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

}
