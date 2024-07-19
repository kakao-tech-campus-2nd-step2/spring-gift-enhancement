package gift.member.controller;

import gift.member.model.Member;
import gift.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public Member register(@RequestParam String email, @RequestParam String password) {
        return memberService.register(email, password);
    }

    @PostMapping("/login")
    public Member login(@RequestParam String email, @RequestParam String password) {
        return memberService.login(email, password);
    }

    @GetMapping("/{memberId}")
    public Member getMemberById(@PathVariable Long memberId) {
        return memberService.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }

    @PutMapping("/{memberId}/email")
    public Member updateEmail(@PathVariable Long memberId, @RequestParam String newEmail) {
        return memberService.updateEmail(memberId, newEmail);
    }

    @PutMapping("/{memberId}/password")
    public Member updatePassword(@PathVariable Long memberId, @RequestParam String newPassword) {
        return memberService.updatePassword(memberId, newPassword);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }
}