package gift.model;

import static gift.util.Constants.CATEGORY_DESCRIPTION_SIZE_LIMIT;
import static gift.util.Constants.INVALID_COLOR;
import static gift.util.Constants.REQUIRED_FIELD_MISSING;
import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Category 모델 생성 테스트")
    public void testCreateCategory() {
        Category category = new Category("Category", "#000000", "imageUrl", "description");

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertThat(violations).isEmpty();
        assertThat(category.getName()).isEqualTo("Category");
        assertThat(category.getColor()).isEqualTo("#000000");
        assertThat(category.getImageUrl()).isEqualTo("imageUrl");
        assertThat(category.getDescription()).isEqualTo("description");
    }

    @Test
    @DisplayName("유효하지 않은 색상 코드 테스트")
    public void testInvalidColor() {
        Category category = new Category("Category", "000000", "imageUrl", "description");

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("color") &&
                violation.getMessage().equals(INVALID_COLOR)
        );
    }

    @Test
    @DisplayName("색상 코드가 없는 경우 테스트")
    public void testNoColor() {
        Category category = new Category("Category", null, "imageUrl", "description");

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("color") &&
                violation.getMessage().equals(REQUIRED_FIELD_MISSING)
        );
    }

    @Test
    @DisplayName("이미지 URL이 없는 경우 테스트")
    public void testNoImageUrl() {
        Category category = new Category("Category", "#000000", null, "description");

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("imageUrl") &&
                violation.getMessage().equals(REQUIRED_FIELD_MISSING)
        );
    }

    @Test
    @DisplayName("카테고리 이름이 없는 경우 테스트")
    public void testNoName() {
        Category category = new Category(null, "#000000", "imageUrl", "description");

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("name") &&
                violation.getMessage().equals(REQUIRED_FIELD_MISSING)
        );
    }

    @Test
    @DisplayName("설명이 255자를 초과하는 경우 테스트")
    public void testDescriptionTooLong() {
        String longDescription = "^".repeat(256);
        Category category = new Category("Category", "#000000", "imageUrl", longDescription);

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("description") &&
                violation.getMessage().equals(CATEGORY_DESCRIPTION_SIZE_LIMIT)
        );
    }
}
