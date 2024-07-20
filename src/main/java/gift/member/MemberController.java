package gift.member;

import gift.common.auth.AuthService;
import gift.member.model.MemberRequest;
import gift.member.model.MemberResponse;
import jakarta.validation.Valid;
import java.net.URI;
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
    public ResponseEntity<MemberResponse> register(
        @Valid @RequestBody MemberRequest memberRequest) {
        Long memberId = memberService.insertMember(memberRequest);
        return ResponseEntity.created(URI.create("/api/members/" + memberId))
            .body(authService.getToken(memberRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<MemberResponse> login(
        @Valid @RequestBody MemberRequest memberRequest) {
        return ResponseEntity.ok(authService.getToken(memberRequest));
    }
}
