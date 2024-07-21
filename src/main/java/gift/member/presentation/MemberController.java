package gift.member.presentation;

import gift.auth.TokenService;
import gift.member.application.MemberResponse;
import gift.member.application.MemberService;
import gift.member.presentation.request.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;
    private final TokenService tokenService;

    public MemberController(MemberService memberService, TokenService tokenService) {
        this.memberService = memberService;
        this.tokenService = tokenService;
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(
            @RequestBody MemberJoinRequest request
    ) {
        Long memberId = memberService.join(request.toCommand());
        String token = tokenService.createToken(memberId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        return ResponseEntity.ok().headers(headers).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody MemberLoginRequest request
    ) {
        Long memberId = memberService.login(request.toCommand());
        String token = tokenService.createToken(memberId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        return ResponseEntity.ok().headers(headers).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> findById(
            @PathVariable("id") Long memberId
    ) {
        return ResponseEntity.ok(memberService.findById(memberId));
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> findAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @PutMapping("/email")
    public ResponseEntity<?> updateEmail(
            @RequestBody MemberEmailUpdateRequest request,
            ResolvedMember resolvedMember
    ) {
        memberService.updateEmail(request.toCommand(), resolvedMember);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(
            @RequestBody MemberPasswordUpdateRequest request,
            ResolvedMember resolvedMember
    ) {
        memberService.updatePassword(request.toCommand(), resolvedMember);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> delete(
            ResolvedMember resolvedMember
    ) {
        memberService.delete(resolvedMember);
        return ResponseEntity.noContent().build();
    }
}

