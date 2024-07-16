package gift.service;

import gift.constants.Messages;
import gift.domain.Member;
import gift.dto.request.MemberRequest;
import gift.dto.response.MemberResponse;
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
    public void save(MemberRequest memberRequest){
        memberRepository.save(memberRequest.toEntity());
    }

    @Transactional(readOnly = true)
    public boolean checkMemberExistsByIdAndPassword(String email, String password) {
        memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new MemberNotFoundException(Messages.NOT_FOUND_MEMBER));
        return true;
    }

    @Transactional(readOnly = true)
    public MemberResponse findByEmail(String email){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(Messages.NOT_FOUND_MEMBER));
        return MemberResponse.from(member);
    }
}
