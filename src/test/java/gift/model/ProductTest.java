package gift.model;

import static gift.util.constants.ProductConstants.NAME_INVALID_CHARACTERS;
import static gift.util.constants.ProductConstants.NAME_REQUIRES_APPROVAL;
import static gift.util.constants.ProductConstants.NAME_SIZE_LIMIT;
import static gift.util.constants.ProductConstants.OPTION_NAME_DUPLICATE;
import static gift.util.constants.ProductConstants.OPTION_REQUIRED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Product 모델 생성 테스트")
    public void testCreateProduct() {
        Category category = new Category("Category", "#000000", "imageUrl", "description");
        Option option1 = new Option(1L, "Option1", 100, null);
        Option option2 = new Option(2L, "Option2", 200, null);
        List<Option> options = List.of(option1, option2);
        Product product = new Product(1L, "Product1", 100, "imageUrl1", category, options);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertThat(violations).isEmpty();
        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("Product1");
        assertThat(product.getPrice()).isEqualTo(100);
        assertThat(product.getImageUrl()).isEqualTo("imageUrl1");
        assertThat(product.getCategoryName()).isEqualTo("Category");
        assertThat(product.getOptions()).containsExactlyInAnyOrder(option1, option2);
    }

    @Test
    @DisplayName("옵션 없는 Product 모델 생성 테스트")
    public void testCreateProductWithoutOptions() {
        Category category = new Category("Category", "#000000", "imageUrl", "description");

        assertThatThrownBy(() -> new Product(1L, "Product1", 100, "imageUrl1", category, List.of()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(OPTION_REQUIRED);
    }

    @Test
    @DisplayName("중복 옵션 이름 테스트")
    public void testCreateProductWithDuplicateOptions() {
        Category category = new Category("Category", "#000000", "imageUrl", "description");
        Option option1 = new Option(1L, "Option1", 100, null);
        Option option2 = new Option(2L, "Option1", 200, null);
        List<Option> options = List.of(option1, option2);

        assertThatThrownBy(() -> new Product(1L, "Product1", 100, "imageUrl1", category, options))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(OPTION_NAME_DUPLICATE);
    }

    @Test
    @DisplayName("Product 모델 업데이트 테스트")
    public void testUpdateProduct() {
        Category category = new Category("Category", "#000000", "imageUrl", "description");
        Option option1 = new Option(1L, "Option1", 100, null);
        Option option2 = new Option(2L, "Option2", 200, null);
        List<Option> options = List.of(option1, option2);
        Product product = new Product(1L, "Product1", 100, "imageUrl1", category, options);
        Category newCategory = new Category("New Category", "#111111", "newImageUrl",
            "newDescription");
        Option newOption = new Option(3L, "Option3", 300, null);
        List<Option> newOptions = List.of(newOption);

        product.update("Product2", 200, "imageUrl2", newCategory, newOptions);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertThat(violations).isEmpty();
        assertThat(product.getName()).isEqualTo("Product2");
        assertThat(product.getPrice()).isEqualTo(200);
        assertThat(product.getImageUrl()).isEqualTo("imageUrl2");
        assertThat(product.getCategoryName()).isEqualTo("New Category");
        assertThat(product.getOptions()).containsExactly(newOption);
    }

    @Test
    @DisplayName("유효하지 않은 상품 이름 - 길이 초과")
    public void testInvalidProductNameSize() {
        Category category = new Category("Category", "#000000", "imageUrl", "description");
        Option option1 = new Option(1L, "Option1", 100, null);
        List<Option> options = List.of(option1);
        Product product = new Product(1L, "가나다라마바사아자차카타파하가나다라마바사아자차카타파하", 100, "imageUrl1",
            category, options);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("name") &&
                violation.getMessage().equals(NAME_SIZE_LIMIT)
        );
    }

    @Test
    @DisplayName("유효하지 않은 상품 이름 - 특수 문자 포함")
    public void testInvalidProductNamePattern() {
        Category category = new Category("Category", "#000000", "imageUrl", "description");
        Option option1 = new Option(1L, "Option1", 100, null);
        List<Option> options = List.of(option1);
        Product product = new Product(1L, "Invalid@Name!", 100, "imageUrl1", category, options);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("name") &&
                violation.getMessage().equals(NAME_INVALID_CHARACTERS)
        );
    }

    @Test
    @DisplayName("유효하지 않은 상품 이름 - '카카오' 포함")
    public void testInvalidProductNameContainsKakao() {
        Category category = new Category("Category", "#000000", "imageUrl", "description");
        Option option1 = new Option(1L, "Option1", 100, null);
        List<Option> options = List.of(option1);
        Product product = new Product(1L, "카카오톡", 100, "imageUrl1", category, options);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("name") &&
                violation.getMessage().equals(NAME_REQUIRES_APPROVAL)
        );
    }
}
