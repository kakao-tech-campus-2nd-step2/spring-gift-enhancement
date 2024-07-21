package gift.member.controller;

import gift.member.model.Member;
import gift.member.repository.MemberRepository;
import gift.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/register")
    public Member register(@RequestParam String email, @RequestParam String password) {
        return memberService.register(email, password);
    }

    @PostMapping("/login")
    public Member login(@RequestParam String email, @RequestParam String password) {
        return memberService.login(email, password);
    }

    @GetMapping("/{memberId}")
    public Member getMemberById(@PathVariable("memberId") Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }

    @PutMapping("/{memberId}/email")
    public Member updateEmail(@PathVariable("memberId") Long id, @RequestParam String newEmail) {
        return memberService.updateEmail(id, newEmail);
    }

    @PutMapping("/{memberId}/password")
    public Member updatePassword(@PathVariable("memberId") Long id, @RequestParam String newPassword) {
        return memberService.updatePassword(id, newPassword);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable("memberId") Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}