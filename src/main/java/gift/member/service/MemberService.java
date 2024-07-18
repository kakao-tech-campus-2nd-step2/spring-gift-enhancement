package gift.member.service;

import gift.member.model.Member;
import gift.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member register(String email, String password) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }

        Member member = new Member(email, password);
        return memberRepository.save(member);
    }

    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("옳지 않은 이메일이나 비밀번호 입니다."));

        if (!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("옳지 않은 이메일이나 비밀번호 입니다.");
        }

        return member;
    }

    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Member updateEmail(Long memberId, String newEmail) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        member.updateEmail(newEmail);
        return memberRepository.save(member);
    }

    public Member updatePassword(Long memberId, String newPassword) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        member.updatePassword(newPassword);
        return memberRepository.save(member);
    }

    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        memberRepository.delete(member);
    }
}