package gift.product.config;

import gift.product.JwtCookieToHeaderInterceptor;
import gift.product.TokenValidationInterceptor;
import gift.product.repository.AuthRepository;
import gift.product.repository.CategoryRepository;
import gift.product.repository.ProductRepository;
import gift.product.repository.WishRepository;
import gift.product.service.AuthService;
import gift.product.service.CategoryService;
import gift.product.service.ProductService;
import gift.product.service.WishService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ProductRepository productRepository;
    private final WishRepository wishRepository;
    private final AuthRepository authRepository;
    private final CategoryRepository categoryRepository;


    public WebConfig(ProductRepository productRepository, WishRepository wishRepository,
        AuthRepository authRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.wishRepository = wishRepository;
        this.authRepository = authRepository;
        this.categoryRepository = categoryRepository;
    }

    @Bean
    public ProductService productService() {
        return new ProductService(productRepository, categoryRepository);
    }

    @Bean
    public WishService wishService() {
        return new WishService(wishRepository, productService(), authRepository);
    }

    @Bean
    public AuthService authService() {
        return new AuthService(authRepository);
    }

    @Bean
    public CategoryService categoryService() {
        return new CategoryService(categoryRepository);
    }

    @Bean
    public TokenValidationInterceptor tokenValidationInterceptor() {
        return new TokenValidationInterceptor(authRepository);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenValidationInterceptor())
            .order(2)
            .addPathPatterns("/admin/wishes/**")
            .addPathPatterns("/api/wishes/**");

        registry.addInterceptor(new JwtCookieToHeaderInterceptor())
            .order(1)
            .addPathPatterns("/admin/wishes/**");
    }
}
