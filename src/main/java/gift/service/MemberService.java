package gift.service;

import gift.dto.MemberResponseDto;
import gift.entity.Member;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final TokenService tokenService;

    public MemberService(MemberRepository memberRepository, TokenService tokenService) {
        this.memberRepository = memberRepository;
        this.tokenService = tokenService;
    }

    public MemberResponseDto save(String email, String password) {

        Member newMember = new Member(email, password);

        Member actualMember = memberRepository.save(newMember);
        String token = generateTokenFrom(actualMember.getEmail());
        return new MemberResponseDto(actualMember, token);
    }


    public List<Member> getAll() {
        return memberRepository.findAll();
    }

    public List<MemberResponseDto> getAllMemberResponseDto() {
        return getAll().stream().map(this::fromEntity).toList();
    }

    public String generateTokenFrom(String userEmail) {
        return findUserIdFrom(userEmail).toString();
    }

    private Long findUserIdFrom(String userEmail) {
        return memberRepository.findByEmail(userEmail).getId();
    }

    public boolean login(String email, String password) throws AuthenticationException {
        MemberResponseDto dbUserDto = fromEntity(memberRepository.findByEmail(email));

        return validatePassword(password, dbUserDto.getPassword());
    }

    public MemberResponseDto fromEntity(Member member) {
        return new MemberResponseDto(member.getId(), member.getEmail(), member.getPassword());
    }

    private boolean validatePassword(String inputPassword, String dbUserPassword) throws AuthenticationException {

        if (inputPassword.equals(dbUserPassword)) {
            return true;
        }
        throw new AuthenticationException("Invalid password");
    }
}
