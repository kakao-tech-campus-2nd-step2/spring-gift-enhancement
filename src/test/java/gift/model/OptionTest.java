package gift.model;

import static gift.util.constants.OptionConstants.NAME_INVALID_CHARACTERS;
import static gift.util.constants.OptionConstants.NAME_SIZE_LIMIT;
import static gift.util.constants.OptionConstants.QUANTITY_MAX;
import static gift.util.constants.OptionConstants.QUANTITY_MIN;
import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OptionTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Option 모델 생성 테스트")
    public void testCreateOption() {
        Product product = new Product();
        Option option = new Option(1L, "Option1", 100, product);

        Set<ConstraintViolation<Option>> violations = validator.validate(option);

        assertThat(violations).isEmpty();
        assertThat(option.getId()).isEqualTo(1L);
        assertThat(option.getName()).isEqualTo("Option1");
        assertThat(option.getQuantity()).isEqualTo(100);
        assertThat(option.getProduct()).isEqualTo(product);
    }

    @Test
    @DisplayName("Option 모델 업데이트 테스트")
    public void testUpdateOption() {
        Product product = new Product();
        Option option = new Option(1L, "Option1", 100, product);
        Product newProduct = new Product();
        option.update("Option2", 200, newProduct);

        Set<ConstraintViolation<Option>> violations = validator.validate(option);

        assertThat(violations).isEmpty();
        assertThat(option.getName()).isEqualTo("Option2");
        assertThat(option.getQuantity()).isEqualTo(200);
        assertThat(option.getProduct()).isEqualTo(newProduct);
    }

    @Test
    @DisplayName("유효하지 않은 옵션 이름 - 길이 초과")
    public void testInvalidOptionNameSize() {
        Product product = new Product();
        Option option = new Option(1L, "가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하", 100, product);

        Set<ConstraintViolation<Option>> violations = validator.validate(option);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("name") &&
                violation.getMessage().equals(NAME_SIZE_LIMIT)
        );
    }

    @Test
    @DisplayName("유효하지 않은 옵션 이름 - 특수 문자 포함")
    public void testInvalidOptionNamePattern() {
        Product product = new Product();
        Option option = new Option(1L, "Invalid@Name!", 100, product);

        Set<ConstraintViolation<Option>> violations = validator.validate(option);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("name") &&
                violation.getMessage().equals(NAME_INVALID_CHARACTERS)
        );
    }

    @Test
    @DisplayName("유효하지 않은 옵션 수량 - 최소 미달")
    public void testInvalidOptionQuantityMin() {
        Product product = new Product();
        Option option = new Option(1L, "Option1", 0, product);

        Set<ConstraintViolation<Option>> violations = validator.validate(option);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("quantity") &&
                violation.getMessage().equals(QUANTITY_MIN)
        );
    }

    @Test
    @DisplayName("유효하지 않은 옵션 수량 - 최대 초과")
    public void testInvalidOptionQuantityMax() {
        Product product = new Product();
        Option option = new Option(1L, "Option1", 100000000, product);

        Set<ConstraintViolation<Option>> violations = validator.validate(option);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("quantity") &&
                violation.getMessage().equals(QUANTITY_MAX)
        );
    }
}
