package gift.service;

import gift.DTO.member.LoginRequest;
import gift.DTO.member.LoginResponse;
import gift.DTO.member.SignupRequest;
import gift.DTO.member.SignupResponse;
import gift.domain.Member;
import gift.exception.member.DuplicatedEmailException;
import gift.exception.member.InvalidAccountException;
import gift.exception.member.PasswordMismatchException;
import gift.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    public LoginResponse loginMember(LoginRequest loginRequest) {
        Optional<Member> member = memberRepository.findByEmail(loginRequest.getEmail());
        member.orElseThrow(
            () -> new InvalidAccountException());
        Member registeredMember = member.get();

        validatePassword(registeredMember, loginRequest.getPassword());

        String token = tokenService.generateToken(registeredMember);
        return new LoginResponse(token);
    }

    @Transactional(readOnly = true)
    public Member getMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                                .orElseThrow(() -> new InvalidAccountException());
        return member;
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
