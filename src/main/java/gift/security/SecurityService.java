package gift.security;

import gift.config.JwtConfig;
import gift.domain.Member;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public String generateJwtToken(Member member) {
        return JwtConfig.generateToken(member);
    }
}
