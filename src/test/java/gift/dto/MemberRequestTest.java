package gift.dto;

import static gift.util.constants.ProductConstants.REQUIRED_FIELD_MISSING;
import static org.assertj.core.api.Assertions.assertThat;

import gift.dto.member.MemberRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("유효한 회원 가입 요청")
    public void testRegisterMemberValid() {
        MemberRequest memberDTO = new MemberRequest(null, "valid@example.com", "validpassword");

        Set<ConstraintViolation<MemberRequest>> violations = validator.validate(memberDTO);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("이메일이 null인 회원 가입 요청")
    public void testRegisterMemberNullEmail() {
        MemberRequest memberDTO = new MemberRequest(null, null, "validpassword");

        Set<ConstraintViolation<MemberRequest>> violations = validator.validate(memberDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("email") &&
                violation.getMessage().equals(REQUIRED_FIELD_MISSING)
        );
    }

    @Test
    @DisplayName("비밀번호가 null인 회원 가입 요청")
    public void testRegisterMemberNullPassword() {
        MemberRequest memberDTO = new MemberRequest(null, "valid@example.com", null);

        Set<ConstraintViolation<MemberRequest>> violations = validator.validate(memberDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("password") &&
                violation.getMessage().equals(REQUIRED_FIELD_MISSING)
        );
    }
}
