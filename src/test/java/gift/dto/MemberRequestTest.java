package gift.dto;

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
    @DisplayName("유효하지 않은 이메일로 회원 가입 요청")
    public void testRegisterMemberInvalidEmail() {
        MemberRequest memberDTO = new MemberRequest(null, "invalid-email", "validpassword");

        Set<ConstraintViolation<MemberRequest>> violations = validator.validate(memberDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("email") &&
                violation.getMessage().equals("유효한 이메일 주소를 입력해야 합니다.")
        );
    }

    @Test
    @DisplayName("빈 이메일로 회원 가입 요청")
    public void testRegisterMemberBlankEmail() {
        MemberRequest memberDTO = new MemberRequest(null, "", "validpassword");

        Set<ConstraintViolation<MemberRequest>> violations = validator.validate(memberDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("email") &&
                violation.getMessage().equals("이메일은 필수 입력 항목입니다.")
        );
    }

    @Test
    @DisplayName("유효하지 않은 비밀번호로 회원 가입 요청")
    public void testRegisterMemberInvalidPassword() {
        MemberRequest memberDTO = new MemberRequest(null, "valid@example.com", "123");

        Set<ConstraintViolation<MemberRequest>> violations = validator.validate(memberDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("password") &&
                violation.getMessage().equals("비밀번호는 최소 4자리 이상이어야 합니다.")
        );
    }

    @Test
    @DisplayName("빈 비밀번호로 회원 가입 요청")
    public void testRegisterMemberBlankPassword() {
        MemberRequest memberDTO = new MemberRequest(null, "valid@example.com", "");

        Set<ConstraintViolation<MemberRequest>> violations = validator.validate(memberDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("password") &&
                (violation.getMessage().equals("비밀번호는 필수 입력 항목입니다.") ||
                    violation.getMessage().equals("비밀번호는 최소 4자리 이상이어야 합니다."))
        );
    }
}
