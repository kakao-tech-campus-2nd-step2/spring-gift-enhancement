package gift.domain.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  String 필드에 들어가는 문자열이 일부 특수문자와 공백, 영어, 한글만 허용하게 하는 Validator
 * */
@Constraint(validatedBy = { RestrictedSpecialCharsValidator.class })
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RestrictedSpecialChars {

    String message() default "(), [], +, -, &, /, _ 이외의 특수 문자는 사용할 수 없습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // 허용할 특수문자
    String value() default "()[]+-&/_";
}
