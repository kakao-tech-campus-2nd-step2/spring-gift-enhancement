package gift.service.member;

import gift.controller.member.dto.MemberResponse;
import gift.global.auth.jwt.JwtProvider;
import gift.model.member.Member;
import gift.global.validate.InvalidAuthRequestException;
import gift.global.validate.NotFoundException;
import gift.repository.member.MemberRepository;
import gift.service.member.dto.MemberCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public MemberService(MemberRepository memberRepository, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    //@Transactional
    public void register(MemberCommand.Create command) {
        if (memberRepository.existsByEmail(command.email())) {
            throw new InvalidAuthRequestException("User already exists.");
        }
        memberRepository.save(command.toEntity());
    }

    @Transactional(readOnly = true)
    public String login(MemberCommand.Login command) {
        Member member = memberRepository.findByEmail(command.email())
            .orElseThrow(() -> new NotFoundException("User not found."));

        if (!member.verifyPassword(command.password())) {
            throw new InvalidAuthRequestException("Password is incorrect.");
        }
        return jwtProvider.createToken(member.getId(), member.getRole());
    }

    @Transactional(readOnly = true)
    public MemberResponse.Info getUser(Long memberId) {
        var member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NotFoundException("User not found."));
        return MemberResponse.Info.from(member);
    }
}
