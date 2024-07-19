package gift.global.component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminLoginInterceptor implements HandlerInterceptor {
    private final TokenComponent tokenComponent;

    @Autowired
    private AdminLoginInterceptor(TokenComponent tokenComponent) {
        this.tokenComponent = tokenComponent;
    }

    // 토큰 검증을 마치고 왔으므로 admin인지만 검증
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!tokenComponent.getIsAdmin(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "어드민 권한이 없습니다.");
        }

        return true;
    }
}
