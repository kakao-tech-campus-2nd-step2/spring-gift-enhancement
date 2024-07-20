package gift.resolver;

import gift.config.JwtProvider;
import gift.config.LoginMember;
import gift.exception.ErrorCode;
import gift.exception.GiftException;
import gift.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final AuthHeaderManager authHeaderManager;

    public LoginMemberArgumentResolver(MemberService memberService, JwtProvider jwtProvider, AuthHeaderManager authHeaderManager) {
        this.memberService = memberService;
        this.jwtProvider = jwtProvider;
        this.authHeaderManager = authHeaderManager;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(LoginMember.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = authHeaderManager.extractToken(request);

        if (!jwtProvider.isVerified(token)) {
            throw new GiftException(ErrorCode.INVALID_TOKEN);
        }

        Long memberId = Long.parseLong(jwtProvider.getClaims(token).getSubject());

        return memberService.getMember(memberId);
    }

}
