package gift.controller;

import gift.dto.MemberResponseDto;
import gift.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiMemberListController {
    MemberService memberService;

    public ApiMemberListController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/user/list")
    public MemberResponseDto UserList() {
        MemberResponseDto memberResponseDto = memberService.getAllAndReturnMemberResponseDto();
        memberResponseDto.setHttpStatus(HttpStatus.OK);
        return memberResponseDto;
    }
}
