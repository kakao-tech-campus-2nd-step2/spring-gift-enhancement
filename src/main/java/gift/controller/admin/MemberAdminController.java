package gift.controller.admin;

import gift.dto.member.MemberRequest;
import gift.dto.member.MemberResponse;
import gift.model.Member;
import gift.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/members")
public class MemberAdminController {

    private final MemberService memberService;

    public MemberAdminController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("")
    public String getAllMembers(Model model) {
        model.addAttribute("members", memberService.getAllMembers());
        return "members";
    }

    @GetMapping("/new")
    public String showAddMemberForm(Model model) {
        model.addAttribute("member", new MemberRequest(null, "", ""));
        return "member_form";
    }

    @PostMapping("")
    public String addMember(@Valid @ModelAttribute("member") MemberRequest memberDTO,
        BindingResult result) {
        if (result.hasErrors()) {
            return "member_form";
        }
        memberService.registerMember(memberDTO);
        return "redirect:/admin/members";
    }

    @GetMapping("/{id}/edit")
    public String showEditMemberForm(@PathVariable("id") Long id, Model model) {
        MemberResponse memberResponse = memberService.getMemberById(id);
        model.addAttribute("member", new Member(memberResponse.id(), memberResponse.email(), null));
        return "member_edit";
    }

    @PutMapping("/{id}")
    public String updateMember(@PathVariable("id") Long id,
        @Valid @ModelAttribute MemberRequest memberRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("member", memberRequest);
            model.addAttribute("org.springframework.validation.BindingResult.member", result);
            return "member_edit";
        }
        memberService.updateMember(id, memberRequest);
        return "redirect:/admin/members";
    }

    @DeleteMapping("/{id}")
    public String deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return "redirect:/admin/members";
    }
}
