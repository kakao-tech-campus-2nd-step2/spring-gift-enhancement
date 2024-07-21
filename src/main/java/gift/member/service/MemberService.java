package gift.member.service;

import gift.member.model.Member;
import gift.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public Member register(String email, String password) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }

        Member member = new Member(email, password);
        return memberRepository.save(member);
    }

    @Transactional
    public Member login(String email, String password) {
        // 이메일 있는지 확인
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("옳지 않은 이메일 입니다."));

        // 비밀번호가 맞는지 확인
        if (!member.checkPassword(password)) {
            throw new IllegalArgumentException("옳지 않은 비밀번호 입니다.");
        }

        return member;
    }

    @Transactional
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Transactional
    public Member updateEmail(Long id, String newEmail) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        member.updateEmail(newEmail);
        return memberRepository.save(member);
    }

    @Transactional
    public Member updatePassword(Long id, String newPassword) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        member.updatePassword(newPassword);
        return memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        memberRepository.delete(member);
    }
}