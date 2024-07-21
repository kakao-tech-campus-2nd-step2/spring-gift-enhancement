package gift.product.service;

import gift.product.dto.MemberDTO;
import gift.product.model.Member;
import gift.product.repository.MemberRepository;
import gift.product.util.JwtUtil;
import gift.product.validation.MemberValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final MemberValidation memberValidation;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(
            MemberRepository memberRepository,
            JwtUtil jwtUtil,
            MemberValidation memberValidation,
            PasswordEncoder passwordEncoder
    ) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
        this.memberValidation = memberValidation;
        this.passwordEncoder = passwordEncoder;
    }

    public Map<String, String> signUp(MemberDTO memberDTO) {
        System.out.println("[MemberService] signUp()");
        memberValidation.signUpValidation(memberDTO.getEmail());
        Member member = convertDTOToMember(memberDTO);
        memberRepository.save(member);
        String token = jwtUtil.generateToken(member.getEmail());
        return responseToken(token);
    }

    public Map<String, String> login(MemberDTO memberDTO) {
        System.out.println("[MemberService] login()");
        memberValidation.loginValidation(memberDTO);
        String token = jwtUtil.generateToken(memberDTO.getEmail());
        return responseToken(token);
    }

    public Map<String, String> responseToken(String token) {
        System.out.println("[MemberService] responseToken()");
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }

    public Member convertDTOToMember(MemberDTO memberDTO) {
        System.out.println("[MemberService] convertDTOToMember()");
        return new Member(
                memberDTO.getEmail(),
                passwordEncoder.encode(memberDTO.getPassword())
        );
    }
}
