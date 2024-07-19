package gift.service;

import gift.domain.Member;
import gift.dto.request.MemberRequest;
import gift.exception.DuplicateMemberEmailException;
import gift.exception.InvalidCredentialsException;
import gift.exception.MemberNotFoundException;
import gift.repository.member.MemberSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static gift.exception.ErrorCode.*;

@Service
@Transactional
public class MemberService {

    private final MemberSpringDataJpaRepository memberRepository;

    @Autowired
    public MemberService(MemberSpringDataJpaRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member register(MemberRequest memberRequest) {
        Optional<Member> oldMember = memberRepository.findByEmail(memberRequest.getEmail());
        if (oldMember.isPresent()) {
            throw new DuplicateMemberEmailException(DUPLICATE_MEMBER_EMAIL);
        }
        Member member = new Member(memberRequest.getEmail(), memberRequest.getPassword());
        memberRepository.save(member);
        return member;
    }

    public Member authenticate(MemberRequest memberRequest) {
        Member member = memberRepository.findByEmail(memberRequest.getEmail())
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        if (!memberRequest.getPassword().equals(member.getPassword())) {
            throw new InvalidCredentialsException(INVALID_CREDENTIAL);
        }
        return member;
    }

}
