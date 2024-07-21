package gift.global.resolver;

import gift.category.entity.Category;
import gift.global.annotation.ProductInfo;
import gift.global.component.TokenComponent;
import gift.global.dto.ProductInfoDto;
import gift.product.dto.ProductResponseDto;
import gift.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

// wishlist api에서 product 관련 처리를 하지 않기 위해 처리해주는 resolver
@Component
public class ProductInfoResolver implements HandlerMethodArgumentResolver {

    private final ProductService productService;

    @Autowired
    public ProductInfoResolver(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isUserAnnotation = parameter.hasParameterAnnotation(ProductInfo.class);
        boolean isProductDto = parameter.getParameterType().equals(ProductInfoDto.class);

        return isUserAnnotation && isProductDto;
    }

    @Override
    public ProductInfoDto resolveArgument(MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        long productId = getProductId(webRequest.getParameter("product-id"));
        ProductResponseDto productResponseDto = productService.selectProduct(productId);

        String name = productResponseDto.name();
        int price = productResponseDto.price();
        String imageUrl = productResponseDto.imageUrl();
        Long categoryId = productResponseDto.categoryId();
        String categoryName = productResponseDto.categoryName();
        String categoryImageUrl = productResponseDto.imageUrl();

        ProductInfoDto productInfoDto = new ProductInfoDto(productId, name, price, imageUrl,
            categoryId, categoryName, categoryImageUrl);

        return productInfoDto;
    }

    // 옳지 않은 경로로 product id가 없으면 오류 검출
    private Long getProductId(String productIdParam) {
        if (productIdParam == null) {
            throw new IllegalArgumentException("올바른 제품을 골라주세요.");
        }

        return Long.parseLong(productIdParam);
    }
}
