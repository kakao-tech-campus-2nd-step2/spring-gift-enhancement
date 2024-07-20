package gift.controller;


import gift.dto.MemberDto;
import gift.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RequestMapping("/members")
@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewMember(@RequestBody MemberDto memberDto) {
        memberService.registerNewMember(memberDto);
        String token = memberService.returnToken(memberDto);
        return ResponseEntity.ok().body(Collections.singletonMap("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody MemberDto memberDto) {
        String token = memberService.loginMember(memberDto);
        return ResponseEntity.ok().body(Collections.singletonMap("token", token));
    }

    @GetMapping("/register")
    public String moveToRegister() {
        return "registerMember";
    }

    @GetMapping("/login")
    public String moveToLogin() {
        return "loginMember";
    }
}

