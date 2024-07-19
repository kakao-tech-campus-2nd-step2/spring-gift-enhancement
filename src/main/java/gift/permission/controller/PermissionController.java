package gift.permission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 로그인, 회원가입 view를 반환하는 컨트롤러
@Controller
@RequestMapping("/users")
@Tag(name = "permission-controller", description = "view 반환")
public class PermissionController {

    @GetMapping("/login")
    @Operation
    public String getLoginPage() {
        return "html/login";
    }

    @GetMapping("/registration")
    @Operation
    public String getRegistrationPage() {
        return "html/registration";
    }
}
