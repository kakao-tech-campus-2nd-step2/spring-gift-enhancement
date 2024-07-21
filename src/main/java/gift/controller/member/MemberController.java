package gift.controller.member;

import gift.config.LoginMember;
import gift.controller.auth.AuthController;
import gift.controller.auth.LoginResponse;
import gift.service.MemberService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<Page<MemberResponse>> getAllMembers(@LoginMember LoginResponse member,
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        AuthController.validateAdmin(member);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findAll(pageable));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> getMember(@LoginMember LoginResponse member,
        @PathVariable UUID memberId) {
        AuthController.validateUserOrAdmin(member, memberId);
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findById(memberId));
    }

    @PostMapping("/register")
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody SignUpRequest member) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.save(member));
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponse> updateMember(@LoginMember LoginResponse loginMember,
        @PathVariable UUID memberId, @RequestBody MemberRequest member) {
        AuthController.validateUserOrAdmin(loginMember, memberId);
        return ResponseEntity.status(HttpStatus.OK).body(memberService.update(memberId, member));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@LoginMember LoginResponse loginMember,
        @PathVariable UUID memberId) {
        AuthController.validateUserOrAdmin(loginMember, memberId);
        memberService.delete(memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}