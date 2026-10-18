
package kigali.library.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import kigali.library.rw.domain.Member;
import kigali.library.rw.repository.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Optional<Member> findById(UUID id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> update(UUID id, Member member) {
        return memberRepository.findById(id).map(existingMember -> {
            existingMember.setFirstName(member.getFirstName());
            existingMember.setLastName(member.getLastName());
            existingMember.setMemberNumber(member.getMemberNumber());
            existingMember.setEmail(member.getEmail());
            existingMember.setPhone(member.getPhone());

            return memberRepository.save(existingMember);
        });
    }

    public boolean delete(UUID id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        }

        return false;
    }
}

