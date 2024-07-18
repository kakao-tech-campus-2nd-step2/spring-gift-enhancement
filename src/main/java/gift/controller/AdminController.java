package gift.controller;

import gift.entity.Member;
import gift.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public String adminPage(HttpSession session, Model model) {
        String user = (String) session.getAttribute("user");
        if ("admin@kakao.com".equals(user)) {
            List<Member> members = memberService.getAllMembers();
            model.addAttribute("members", members);
            return "admin";
        } else {
            return "redirect:/home";
        }
    }

    @RequestMapping(value = "/members/{id}", method = RequestMethod.DELETE)
    public String deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return "redirect:/admin";
    }
}
