package gift.service;

import gift.database.JpaMemberRepository;
import gift.dto.LoginMemberToken;
import gift.dto.MemberDTO;
import gift.exceptionAdvisor.exceptions.MemberAuthenticationException;
import gift.model.Member;
import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private JpaMemberRepository jpaMemberRepository;

    private AuthenticationTool authenticationTool;

    public MemberServiceImpl(JpaMemberRepository jpaMemberRepository,
        AuthenticationTool authenticationTool) {
        this.jpaMemberRepository = jpaMemberRepository;
        this.authenticationTool = authenticationTool;
    }

    @Override
    public void register(MemberDTO memberDTO) {
        if (checkEmailDuplication(memberDTO.getEmail())) {
            throw new MemberAuthenticationException("사용할 수 없는 이메일입니다.");
        }
        Member member = new Member(null, memberDTO.getEmail(), memberDTO.getPassword(), memberDTO.getRole());
        jpaMemberRepository.save(member);
    }

    @Override
    public LoginMemberToken login(MemberDTO memberDTO) {
        Member member = findByEmail(memberDTO.getEmail());

        if (memberDTO.getPassword().equals(member.getPassword())) {
            String token = authenticationTool.makeToken(member);
            return new LoginMemberToken(token);
        }

        throw new MemberAuthenticationException();
    }

    @Override
    public boolean checkRole(MemberDTO memberDTO) {
        return false;
    }

    @Override
    public MemberDTO getLoginUser(String token) {
        long id = authenticationTool.parseToken(token);
        Member member = jpaMemberRepository.findById(id).orElseThrow(MemberAuthenticationException::new);

        return new MemberDTO(id,member.getEmail(), member.getPassword(), member.getRole());
    }


    private boolean checkEmailDuplication(String email) {
        try {
            jpaMemberRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private Member findByEmail(String email) {
        try {
            return jpaMemberRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);
        } catch (NoSuchElementException e) {
            throw new MemberAuthenticationException();
        }
    }
}
