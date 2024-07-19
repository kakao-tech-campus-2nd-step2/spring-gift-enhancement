package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.domain.category.entity.Category;
import gift.domain.category.repository.CategoryRepository;
import gift.domain.option.dto.OptionRequest;
import gift.domain.product.repository.ProductRepository;
import gift.domain.product.dto.ProductRequest;
import gift.domain.product.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("findById 테스트")
    void findByIdTest() {
        // given
        OptionRequest optionRequest = new OptionRequest("name", 100);
        ProductRequest request = new ProductRequest("test", 1000, "test.jpg", 1L, optionRequest);
        Category savedCategory = categoryRepository.save(
            new Category("test", "color", "image", "description"));
        Product expected = productRepository.save(dtoToEntity(request, savedCategory));

        // when
        Product actual = productRepository.findById(expected.getId()).orElseThrow();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("save 테스트")
    void saveTest() {
        // given
        OptionRequest optionRequest = new OptionRequest("name", 100);
        ProductRequest request = new ProductRequest("test", 1000, "test.jpg", 1L, optionRequest);
        Category savedCategory = categoryRepository.save(
            new Category("test", "color", "image", "description"));

        Product expected = new Product(request.getName(), request.getPrice(),
            request.getImageUrl(), savedCategory);

        // when
        Product actual = productRepository.save(expected);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl()),
            () -> assertThat(actual.getCategory().getId()).isEqualTo(expected.getCategory().getId())
        );
    }

    @Test
    @DisplayName("delete 테스트")
    void deleteTest() {
        // given
        OptionRequest optionRequest = new OptionRequest("name", 100);
        ProductRequest request = new ProductRequest("test", 1000, "test.jpg", 1L, optionRequest);
        Category savedCategory = categoryRepository.save(
            new Category("test", "color", "image", "description"));
        Product savedProduct = productRepository.save(dtoToEntity(request, savedCategory));

        // when
        productRepository.delete(savedProduct);

        // then
        assertTrue(productRepository.findById(savedProduct.getId()).isEmpty());
    }

    private Product dtoToEntity(ProductRequest productRequest, Category savedCategory) {
        return new Product(productRequest.getName(), productRequest.getPrice(),
            productRequest.getImageUrl(), savedCategory);
    }

}