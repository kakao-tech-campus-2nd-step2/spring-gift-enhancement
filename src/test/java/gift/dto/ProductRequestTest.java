package gift.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gift.dto.product.ProductRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("유효한 상품 추가")
    public void testAddProductValid() {
        ProductRequest productDTO = new ProductRequest(null, "Valid Name", 100, "valid.jpg");

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productDTO);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("이름이 긴 상품 추가")
    public void testAddProductNameTooLong() {
        ProductRequest productDTO = new ProductRequest(null, "This name is definitely too long", 100, "valid.jpg");

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("name") &&
                violation.getMessage().equals("상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있습니다.")
        );
    }

    @Test
    @DisplayName("이름에 유효하지 않은 문자가 포함된 상품 추가")
    public void testAddProductInvalidCharactersInName() {
        ProductRequest productDTO = new ProductRequest(null, "Invalid@Name!", 100, "valid.jpg");

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("name") &&
                violation.getMessage().equals("상품 이름에는 다음 특수 문자의 사용만 허용됩니다: ( ), [ ], +, -, &, /, _")
        );
    }

    @Test
    @DisplayName("이름에 '카카오'가 포함된 상품 추가")
    public void testAddProductInvalidNameContainsKakao() {
        ProductRequest productDTO = new ProductRequest(null, "카카오톡", 100, "valid.jpg");

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("name") &&
                violation.getMessage().equals("\"카카오\"가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.")
        );
    }
}
