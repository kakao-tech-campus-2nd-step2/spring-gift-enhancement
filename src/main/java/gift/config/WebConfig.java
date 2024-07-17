package gift.config;

import gift.auth.AuthenticationInterceptor;
import gift.auth.AuthorizationInterceptor;
import gift.auth.LoginMemberArgumentResolver;
import gift.member.persistence.MemberRepository;
import gift.member.service.JwtProvider;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    public WebConfig(JwtProvider jwtProvider, MemberRepository memberRepository) {
        this.jwtProvider = jwtProvider;
        this.memberRepository = memberRepository;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor(jwtProvider)).order(1);
        registry.addInterceptor(new AuthenticationInterceptor()).order(2);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver(memberRepository));
    }
}
