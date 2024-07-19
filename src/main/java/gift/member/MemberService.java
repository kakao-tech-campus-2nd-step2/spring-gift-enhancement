package gift.member;

import gift.common.auth.LoginMemberDto;
import gift.common.exception.MemberException;
import gift.member.model.Member;
import gift.member.model.MemberRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public LoginMemberDto selectLoginMemberById(Long id) throws MemberException {
        return memberRepository.findById(id)
            .map(LoginMemberDto::from)
            .orElseThrow(() -> new MemberException(MemberErrorCode.FAILURE_LOGIN));
    }

    @Transactional
    public Long insertMember(MemberRequestDto memberRequestDto) {
        Member member = memberRepository.save(memberRequestDto.toEntity());
        return member.getId();
    }
}
