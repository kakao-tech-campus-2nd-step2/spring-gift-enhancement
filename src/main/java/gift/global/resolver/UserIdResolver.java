package gift.global.resolver;

import gift.global.annotation.UserId;
import gift.global.component.TokenComponent;
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
public class UserIdResolver implements HandlerMethodArgumentResolver {

    private final TokenComponent tokenComponent;

    @Autowired
    public UserIdResolver(TokenComponent tokenComponent) {
        this.tokenComponent = tokenComponent;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isUserAnnotation = parameter.hasParameterAnnotation(UserId.class);
        boolean isLong = parameter.getParameterType().equals(Long.class);

        return isUserAnnotation && isLong;
    }

    @Override
    public Long resolveArgument(MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        return tokenComponent.getUserId(token);
    }
}