package gift.service;

import gift.dto.request.MemberRequest;
import gift.entity.Member;
import gift.exception.EmailDuplicateException;
import gift.exception.MemberNotFoundException;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long registerMember(MemberRequest request) {
        memberRepository.findByEmail(request.email())
                .ifPresent(member -> {
                    throw new EmailDuplicateException(member);
                });
        Member member = new Member(request.email(), request.password());
        return memberRepository.save(member).getId();
    }


    public Long login(MemberRequest request) {
        Member registeredMember = memberRepository.findByEmailAndPassword(request.email(), request.password())
                .orElseThrow(MemberNotFoundException::new);
        return registeredMember.getId();
    }

    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }
}
