package gift.controller;

import gift.dto.MemberDto;
import gift.dto.request.LoginRequest;
import gift.dto.request.RegisterRequest;
import gift.dto.response.JwtResponse;
import gift.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest request) {
        MemberDto memberDto = new MemberDto(request.getName(), request.getEmail(), request.getPassword());

        memberService.addMember(memberDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginRequest request) {
        MemberDto memberDto = new MemberDto(request.getEmail(), request.getPassword());

        String jwt = memberService.login(memberDto);

        return ResponseEntity.ok()
                .body(new JwtResponse(jwt));
    }

}
