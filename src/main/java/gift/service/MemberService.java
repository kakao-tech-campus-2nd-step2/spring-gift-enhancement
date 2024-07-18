package gift.service;

import gift.DTO.LoginRequest;
import gift.DTO.LoginResponse;
import gift.DTO.SignupRequest;
import gift.DTO.SignupResponse;
import gift.domain.Member;
import gift.exception.member.DuplicatedEmailException;
import gift.exception.member.InvalidAccountException;
import gift.exception.member.PasswordMismatchException;
import gift.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenService tokenService;

    @Autowired
    public MemberService(MemberRepository memberRepository, TokenService tokenService) {
        this.memberRepository = memberRepository;
        this.tokenService = tokenService;
    }

    @Transactional
    public SignupResponse registerMember(SignupRequest signupRequest) {
        memberRepository.findByEmail(signupRequest.getEmail()).ifPresent(p -> {
            throw new DuplicatedEmailException();
        });
        Member member = new Member(signupRequest.getEmail(), signupRequest.getPassword());
        memberRepository.save(member);

        confirmPassword(signupRequest.getPassword(), signupRequest.getConfirmPassword());

        return new SignupResponse(member.getEmail());
    }

    public LoginResponse loginMember(LoginRequest loginRequest) {
        Optional<Member> member = memberRepository.findByEmail(loginRequest.getEmail());
        member.orElseThrow(
            () -> new InvalidAccountException());
        Member registeredMember = member.get();

        validatePassword(registeredMember, loginRequest.getPassword());

        String token = tokenService.generateToken(registeredMember);
        return new LoginResponse(token);
    }

    public Member getMemberByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        member.orElseThrow(() -> new InvalidAccountException());
        return member.get();
    }

    private void validatePassword(Member member, String password) {
        if (!member.getPassword().equals(password)) {
            throw new InvalidAccountException();
        }
    }

    private void confirmPassword(String password, String passwordConfirm) {
        if(!password.equals(passwordConfirm)) {
            throw new PasswordMismatchException();
        }
    }

}
