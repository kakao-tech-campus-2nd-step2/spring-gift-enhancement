package gift.service;

import gift.dto.MemberResponseDto;
import gift.entity.Member;
import gift.repository.MemberRepositoryInterface;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;

@Service
public class MemberService {
    private final MemberRepositoryInterface memberRepositoryInterface;
    private final TokenService tokenService;

    public MemberService(MemberRepositoryInterface memberRepositoryInterface, TokenService tokenService) {
        this.memberRepositoryInterface = memberRepositoryInterface;
        this.tokenService = tokenService;
    }

    public String save(String email, String password) {

        Member newMember = new Member(email, password);

        Member actualMember = memberRepositoryInterface.save(newMember);
        return generateTokenFrom(actualMember.getEmail());
    }


    public List<MemberResponseDto> getAll() {
        return memberRepositoryInterface.findAll().stream().map(MemberResponseDto::fromEntity).toList();
    }

    public String generateTokenFrom(String userEmail) {
        return findUserIdFrom(userEmail).toString();
    }

    private Long findUserIdFrom(String userEmail) {
        return memberRepositoryInterface.findByEmail(userEmail).getId();
    }

    public boolean login(String email, String password) throws AuthenticationException {
        MemberResponseDto dbUserDto = MemberResponseDto.fromEntity(memberRepositoryInterface.findByEmail(email));

        return validatePassword(password, dbUserDto.getPassword());
    }

    private boolean validatePassword(String inputPassword, String dbUserPassword) throws AuthenticationException {

        if (inputPassword.equals(dbUserPassword)) {
            return true;
        }
        throw new AuthenticationException("Invalid password");
    }
}
