import gift.global.Auth.annotation.LoginUser;
import gift.user.domain.User;
import gift.user.service.UserService;
import gift.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    // 생성자 주입
    public LoginMemberArgumentResolver(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // @LoginMember 어노테이션이 붙은 파라미터를 지원
        return parameter.hasParameterAnnotation(LoginUser.class) &&
            User.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String email = JwtUtil.extractEmail(token);

            if (email != null) {
                return userService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            }
        }

        throw new RuntimeException("Invalid token or token not provided");
    }
}
