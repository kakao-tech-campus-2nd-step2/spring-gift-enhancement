package gift.dto;

import static gift.util.Constants.REQUIRED_FIELD_MISSING;
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
        ProductRequest productDTO = new ProductRequest(null, "Valid Name", 100, "valid.jpg", 1L);

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productDTO);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("필수 필드 누락 - 이름")
    public void testAddProductNameMissing() {
        ProductRequest productDTO = new ProductRequest(null, null, 100, "valid.jpg", 1L);

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("name") &&
                violation.getMessage().equals(REQUIRED_FIELD_MISSING)
        );
    }

    @Test
    @DisplayName("필수 필드 누락 - 가격")
    public void testAddProductPriceMissing() {
        ProductRequest productDTO = new ProductRequest(null, "Valid Name", null, "valid.jpg", 1L);

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("price") &&
                violation.getMessage().equals(REQUIRED_FIELD_MISSING)
        );
    }

    @Test
    @DisplayName("필수 필드 누락 - 이미지 URL")
    public void testAddProductImageUrlMissing() {
        ProductRequest productDTO = new ProductRequest(null, "Valid Name", 100, null, 1L);

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("imageUrl") &&
                violation.getMessage().equals(REQUIRED_FIELD_MISSING)
        );
    }
}
