package gift.member;

import gift.common.auth.AuthService;
import gift.member.model.MemberRequestDto;
import gift.member.model.MemberResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;

    public MemberController(MemberService memberService, AuthService authService) {
        this.memberService = memberService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<MemberResponseDto> register(
        @Valid @RequestBody MemberRequestDto memberRequestDto) {
        memberService.insertMember(memberRequestDto);
        return ResponseEntity.ok()
            .body(authService.getToken(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<MemberResponseDto> login(
        @Valid @RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok()
            .body(authService.getToken(memberRequestDto));
    }
}
