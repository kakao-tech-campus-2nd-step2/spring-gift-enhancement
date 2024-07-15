package gift.product.validation;

import gift.product.dto.MemberDTO;
import gift.product.exception.DuplicateException;
import gift.product.exception.LoginFailedException;
import gift.product.model.Member;
import gift.product.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberValidation {

    public static final String DUPLICATE_EMAIL = "이미 가입된 이메일입니다.";
    public static final String INVALID_INPUT = "이메일 또는 비밀번호를 잘못 입력하였습니다.";

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberValidation(
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signUpValidation(String email) {
        memberRepository.findByEmail(email)
                .orElseThrow(() -> new DuplicateException(DUPLICATE_EMAIL));
    }

    public void loginValidation(MemberDTO memberDTO) {
        Member member = memberRepository.findByEmail(memberDTO.getEmail())
                .orElseThrow(() -> new LoginFailedException(INVALID_INPUT));
        if(!passwordEncoder.matches(memberDTO.getPassword(), member.getPassword()))
            throw new LoginFailedException(INVALID_INPUT);
    }
}
