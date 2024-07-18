package gift.global.resolver;

import gift.global.annotation.PageInfo;
import gift.global.dto.PageInfoDto;
import java.util.Set;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

// products에서 page 단위로 보여주기 위해 보내주는 정보를 처리하는 resolver.
@Component
public class PageInfoResolver implements HandlerMethodArgumentResolver {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;
    // 아직은 하나지만 확장성을 위해 set으로 선언합니다.
    private static final Set<String> PROPERTIES = Set.of("price", "userProduct_product_price");

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isPageAnnotation = parameter.hasParameterAnnotation(PageInfo.class);
        boolean isPageInfo = parameter.getParameterType().equals(PageInfoDto.class);
        return isPageAnnotation && isPageInfo;
    }

    @Override
    public PageInfoDto resolveArgument(MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        int pageNumber = getPageNumber(webRequest.getParameter("page-no"));
        int pageSize = getPageSize(webRequest.getParameter("page-size"));
        Sort sort = getSort(webRequest.getParameter("sort-property"),
            webRequest.getParameter("sort-direction"));

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        return new PageInfoDto(pageRequest);
    }

    // 파라미터가 없으면 0페이지 반환
    private int getPageNumber(String pageNumberParam) {
        if (pageNumberParam == null) {
            return DEFAULT_PAGE_NUMBER;
        }

        return Integer.parseInt(pageNumberParam);
    }

    // 파라미터가 없으면 10개씩 보도록 반환
    private int getPageSize(String pageSizeParam) {
        if (pageSizeParam == null) {
            return DEFAULT_PAGE_SIZE;
        }

        return Integer.parseInt(pageSizeParam);
    }

    // sortDirection(= asc or desc)와 sortProperty(= price)를 받아서 Sort 객체로 반환
    private Sort getSort(String sortProperty, String sortDirection) {
        if (sortProperty == null || sortDirection == null) {
            return Sort.unsorted();
        }

        // 검증 및 필요 객체 추출
        verifyProperty(sortProperty);
        Direction direction = Direction.fromString(sortDirection);

        return Sort.by(direction, sortProperty);
    }

    // sortProperty가 잘못된 경우 검증
    private void verifyProperty(String property) {
        if (!PROPERTIES.contains(property)) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }
    }
}
