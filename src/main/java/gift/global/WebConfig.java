package gift.global;

import gift.domain.annotation.ValidAdminMemberArgumentResolver;
import gift.domain.annotation.ValidMemberArgumentResolver;
import gift.domain.service.MemberService;
import gift.global.util.JwtUtil;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    public WebConfig(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ValidMemberArgumentResolver(memberService, jwtUtil));
        resolvers.add(new ValidAdminMemberArgumentResolver(memberService, jwtUtil));
    }

    public static class Constants {

        public static class Constraints {

            public static final String DEFAULT_ALLOWED_SPECIAL_CHARS = "()[]+-&/_";
        }

        public static class Domain {

            public static class Member {

                public static class Permission {

                    public static final String MEMBER = "member";
                    public static final String ADMIN = "admin";
                }
            }

            public static class Product {

                public static final int NAME_LENGTH_MIN = 1;
                public static final int NAME_LENGTH_MAX = 15;
                public static final String NAME_LENGTH_INVALID_MSG = "공백을 포함하여 최대 15자까지 입력할 수 있습니다.";
                public static final String NAME_INCLUDE_KAKAO_MSG = "'카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.";
            }

            public static class Option {

                public static final int NAME_LENGTH_MIN = 1;
                public static final int NAME_LENGTH_MAX = 50;
                public static final int QUANTITY_RANGE_MIN = 1;
                public static final int QUANTITY_RANGE_MAX = 99_999_999;

                public enum QuantityUpdateAction {

                    ADD("add"), SUBTRACT("subtract");

                    private final String action;

                    QuantityUpdateAction(String action) {
                        this.action = action;
                    }

                    public String toString() {
                        return action;
                    }

                    public static List<String> toList() {
                        return Arrays.stream(values()).map(QuantityUpdateAction::toString).toList();
                    }
                }
            }

            public static class Wish {

                public enum QuantityUpdateAction {

                    NOPE("nope"), CREATE("create"), DELETE("delete"), ADD("add");

                    private final String action;

                    QuantityUpdateAction(String action) {
                        this.action = action;
                    }

                    public String toString() {
                        return action;
                    }
                }
            }
        }
    }
}
