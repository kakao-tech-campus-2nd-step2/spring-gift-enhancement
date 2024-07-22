package gift.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/**").permitAll()  // 모든 엔드포인트 접근 허용
                        .anyRequest().authenticated()
                )
                .csrf().disable()  // CSRF 비활성화
                .headers().frameOptions().disable();  // 프레임 옵션 비활성화

        return http.build();
    }
}