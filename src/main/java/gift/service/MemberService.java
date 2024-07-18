package gift.service;

import gift.config.JwtProvider;
import gift.domain.member.Member;
import gift.dto.MemberDto;
import gift.exception.EmailDuplicateException;
import gift.exception.LoginException;
import gift.exception.MemberNotFoundException;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public MemberService(MemberRepository memberRepository, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    public void addMember(MemberDto dto) {
        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new EmailDuplicateException();
        }

        memberRepository.save(dto.toEntity());
    }

    public String login(MemberDto dto) {
        Member member = memberRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword())
                .orElseThrow(LoginException::new);

        return jwtProvider.create(member);
    }

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

}
