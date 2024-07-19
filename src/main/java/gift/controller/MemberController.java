package gift.controller;

import gift.entity.MemberEntity;
import gift.domain.MemberDTO;
import gift.service.JwtUtil;
import gift.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberController(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return new ResponseEntity<>(Collections.singletonMap("status", "SUCCESS"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody MemberDTO memberDTO) {
        MemberEntity authenticatedMember = memberService.authenticateToken(memberDTO);
        String token = jwtUtil.generateToken(authenticatedMember.getEmail(), authenticatedMember.getId());
        Map<String, String> response = Collections.singletonMap("token", token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}