package gift.domain.member.controller;

import gift.domain.member.dto.MemberRequest;
import gift.domain.member.dto.MemberResponse;
import gift.domain.member.service.MemberService;
import gift.util.dto.JwtResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping()
    public ResponseEntity<Page<MemberResponse>> getAllMember(
        @RequestParam(defaultValue = "0") int pageNo,
        @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<MemberResponse> responses = memberService.getAllMember(pageNo, pageSize);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody MemberRequest memberRequest) {
        String token = memberService.register(memberRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new JwtResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody MemberRequest memberRequest) {

        String token = memberService.login(memberRequest);

        if (token != null) {
            return ResponseEntity.ok(new JwtResponse(token));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}