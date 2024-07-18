package gift.global.resolver;

import gift.global.annotation.UserInfo;
import gift.global.component.TokenComponent;
import gift.global.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

// 사용자가 보내진 않지만 보내야만 하는 것들을 미리 처리하는 resolver
@Component
public class UserInfoResolver implements HandlerMethodArgumentResolver {

    private final TokenComponent tokenComponent;

    @Autowired
    public UserInfoResolver(TokenComponent tokenComponent) {
        this.tokenComponent = tokenComponent;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isUserAnnotation = parameter.hasParameterAnnotation(UserInfo.class);
        boolean isUserInfoDto = parameter.getParameterType().equals(UserInfoDto.class);

        return isUserAnnotation && isUserInfoDto;
    }

    @Override
    public UserInfoDto resolveArgument(MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        Long userId = tokenComponent.getUserId(token);

        return new UserInfoDto(userId);
    }
}
