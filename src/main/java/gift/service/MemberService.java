package gift.service;

import gift.config.JwtProvider;
import gift.exception.ErrorCode;
import gift.domain.member.Member;
import gift.dto.MemberDto;
import gift.exception.GiftException;
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
            throw new GiftException(ErrorCode.DUPLICATED_EMAIL);
        }

        memberRepository.save(dto.toEntity());
    }

    public String login(MemberDto dto) {
        Member member = memberRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword())
                .orElseThrow(() -> new GiftException(ErrorCode.LOGIN_FAILURE));

        return jwtProvider.create(member);
    }

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new GiftException(ErrorCode.MEMBER_NOT_FOUND));
    }

}
