package gift.config;

import gift.member.application.MemberService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final MemberService memberService;

    public WebConfig(AuthInterceptor authInterceptor, MemberService memberService) {
        this.authInterceptor = authInterceptor;
        this.memberService = memberService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/wishlist/**")
                .addPathPatterns("/api/products/**")
                .addPathPatterns("/api/member/**")
                .addPathPatterns("/api/categories/**")
                .excludePathPatterns("/api/member/join")
                .excludePathPatterns("/api/member/login");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MemberArgumentResolver(memberService));
    }
}
