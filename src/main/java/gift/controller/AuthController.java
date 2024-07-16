package gift.controller;

import gift.dto.UserRequestDto;
import gift.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.AuthenticationException;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String main() {
        return "auth/index";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "auth/sign-up";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute UserRequestDto userRequestDto) {
        return userService.save(userRequestDto.getEmail(), userRequestDto.getPassword());
    }

    @PostMapping("/user/login")
    public String login(@ModelAttribute UserRequestDto userRequestDto) throws AuthenticationException {
        if (userService.login(userRequestDto.getEmail(), userRequestDto.getPassword())) {
            return userService.generateTokenFrom(userRequestDto.getEmail());
        }
        throw new AuthenticationException("로그인 실패");
    }
}
