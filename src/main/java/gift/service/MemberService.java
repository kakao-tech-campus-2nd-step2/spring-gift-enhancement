package gift.service;

import gift.config.JwtProvider;
import gift.domain.member.Member;
import gift.exception.EmailDuplicateException;
import gift.exception.LoginException;
import gift.exception.MemberNotFoundException;
import gift.repository.MemberRepository;
import gift.dto.request.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public MemberService(MemberRepository memberRepository, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    public void addMember(RegisterRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new EmailDuplicateException();
        }

        memberRepository.save(request.toEntity());
    }

    public String login(String email, String password) {
        Member member = memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(LoginException::new);

        return jwtProvider.create(member);
    }

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

}
