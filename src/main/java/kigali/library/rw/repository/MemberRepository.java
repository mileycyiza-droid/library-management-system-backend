package kigali.library.rw.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import kigali.library.rw.domain.Member;

public interface MemberRepository extends JpaRepository<Member, UUID> {
}