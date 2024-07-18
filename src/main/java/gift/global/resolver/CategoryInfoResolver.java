package gift.global.resolver;

import gift.category.dto.CategoryResponseDto;
import gift.category.service.CategoryService;
import gift.global.annotation.CategoryInfo;
import gift.global.dto.CategoryInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

// products에서 page 단위로 보여주기 위해 보내주는 정보를 처리하는 resolver.
@Component
public class CategoryInfoResolver implements HandlerMethodArgumentResolver {

    private CategoryService categoryService;

    @Autowired
    public CategoryInfoResolver(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isCategoryAnnotation = parameter.hasParameterAnnotation(CategoryInfo.class);
        boolean isCategoryInfo = parameter.getParameterType().equals(CategoryInfoDto.class);

        return isCategoryAnnotation && isCategoryInfo;
    }

    @Override
    public CategoryInfoDto resolveArgument(MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        long categoryId = getCategoryId(webRequest.getParameter("category-id"));

        CategoryResponseDto categoryResponseDto = categoryService.selectCategory(categoryId);

        return new CategoryInfoDto(categoryId, categoryResponseDto.name(),
            categoryResponseDto.imageUrl());
    }

    // null 처리하는 메서드들
    private long getCategoryId(String categoryId) {
        if (categoryId == null) {
            throw new IllegalArgumentException("잘못된 카테고리를 선택하였습니다.");
        }

        return Long.parseLong(categoryId);
    }
}
