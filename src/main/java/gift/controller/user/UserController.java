package gift.controller.user;

import gift.dto.user.UserRequest;
import gift.model.user.User;
import gift.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/auth")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // 로그인
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, String>> login(@RequestBody UserRequest userRequest) {
        String token = userService.login(userRequest.getEmail(), userRequest.getPassword());
        return ResponseEntity.ok(Map.of("accessToken", token));
    }

    // 회원가입
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody UserRequest userRequest) {
        User user = new User(userRequest.getEmail(), userRequest.getPassword());
        userService.register(user);
        return ResponseEntity.ok("회원가입을 성공하였습니다!");
    }
}
