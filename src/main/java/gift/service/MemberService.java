package gift.service;

import gift.dto.MemberDto;
import gift.jwt.JwtTokenProvider;
import gift.model.member.Member;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public MemberService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberRepository = memberRepository;
    }

    public void registerNewMember(MemberDto memberDto) {
        Member member = new Member(memberDto.email(),memberDto.password(), memberDto.role());
        if(!memberRepository.findByEmail(member.getEmail()).isPresent()){
            throw new RuntimeException("error");
        }
        memberRepository.save(member);
    }

    public String returnToken(MemberDto memberDto){
        Member member = new Member(memberDto.email(),memberDto.password(), memberDto.role());
        return jwtTokenProvider.generateToken(member);
    }

    public String loginMember(MemberDto memberDto) {
        Member member = new Member(memberDto.email(),memberDto.password(), memberDto.role());
        Optional<Member> registeredMember = memberRepository.findByEmail(member.getEmail());
        if (registeredMember.isPresent()){
            throw new RuntimeException();
        }
        if (member.isPasswordEqual(registeredMember.get().getPassword())){
            throw new RuntimeException();
        }
        return jwtTokenProvider.generateToken(member);
    }

    public Optional<Member> findByEmail(String email){
        return memberRepository.findByEmail(email);
    }
}
