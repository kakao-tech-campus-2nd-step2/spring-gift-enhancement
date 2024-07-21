package gift.global.configuration;

import gift.global.component.AdminLoginInterceptor;
import gift.global.component.LoginInterceptor;
import gift.global.resolver.CategoryInfoResolver;
import gift.global.resolver.PageInfoResolver;
import gift.global.resolver.ProductInfoResolver;
import gift.global.resolver.UserInfoResolver;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// WebMvcConfigurer를 구현하는 클래스.
@Configuration
@SpringBootConfiguration
@Order(value = 1)
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final AdminLoginInterceptor adminLoginInterceptor;
    private final UserInfoResolver userInfoResolver;
    private final PageInfoResolver pageInfoResolver;
    private final ProductInfoResolver productInfoResolver;
    private final CategoryInfoResolver categoryInfoResolver;

    @Autowired
    public WebMvcConfig(LoginInterceptor loginInterceptor,
        AdminLoginInterceptor adminLoginInterceptor,
        UserInfoResolver userInfoResolver, PageInfoResolver pageInfoResolver,
        ProductInfoResolver productInfoResolver, CategoryInfoResolver categoryInfoResolver) {
        this.loginInterceptor = loginInterceptor;
        this.adminLoginInterceptor = adminLoginInterceptor;
        this.userInfoResolver = userInfoResolver;
        this.pageInfoResolver = pageInfoResolver;
        this.productInfoResolver = productInfoResolver;
        this.categoryInfoResolver = categoryInfoResolver;
    }

    // 인터셉터를 추가하는 메서드를 재정의하여 loginInterceptor를 등록하도록 함.
    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        // login 인터셉터 추가
        interceptorRegistry
            .addInterceptor(loginInterceptor)
            .order(1)

            // 모든 행동에 대해 검증
            .addPathPatterns("/api/**")
            .addPathPatterns("/users/**")
            .addPathPatterns("/admin/**")

            // 회원가입(+페이지) 및 로그인(+페이지)를 제외하고
            .excludePathPatterns("/users/registration")
            .excludePathPatterns("/users/login")
            .excludePathPatterns("/api/registration")
            .excludePathPatterns("/api/users/login")
            .excludePathPatterns("/api/admin/login");

        // admin 인터셉터 추가
        interceptorRegistry
            .addInterceptor(adminLoginInterceptor)
            .order(2)

            // admin 관련 행동에 대해 검증
            .addPathPatterns("/admin/**")
            .addPathPatterns("/api/admin/**")

            // 로그인을 제외하고
            .excludePathPatterns("/api/admin/login");
    }

    // argument resolver를 추가하는 메서드 재정의
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userInfoResolver);
        resolvers.add(pageInfoResolver);
        resolvers.add(productInfoResolver);
        resolvers.add(categoryInfoResolver);
    }
}
