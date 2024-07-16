package gift.config;


import gift.constant.Constants;
import gift.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class JwtConfig {
    public static String generateToken(String email) {
        Claims claims = (Claims) Jwts.claims();
        claims.put("email", email);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Constants.ONE_DAY_MILLIS))
                .signWith(Keys.hmacShaKeyFor(Constants.SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
}