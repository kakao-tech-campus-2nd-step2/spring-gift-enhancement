package gift.util;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class JwtAspect {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    @Before("@annotation(gift.util.JwtAuthenticated)")
    public void authenticate() {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("JWT Token is missing or does not start with Bearer");
        }

        String token = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 부분만 추출

        String email = jwtUtil.extractUsername(token);

        if (!jwtUtil.validateToken(token, email)) {
            throw new RuntimeException("JWT Token is not valid");
        }
    }
}
