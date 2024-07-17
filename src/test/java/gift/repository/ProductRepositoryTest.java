package gift.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import gift.domain.Category;
import gift.domain.Product;
import gift.exception.NoSuchProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    private Category category;
    private Product product;

    @BeforeEach
    void setup() {
        category = new Category("test", "#FFFFFF", "testImageUrl", "test");
        category = categoryRepository.save(category);
        product = new Product("아이스 아메리카노", 4500, "image", category);
    }

    @DisplayName("상품 추가")
    @Test
    void save() {
        // given
        Product expected = product;

        // when
        Product actual = productRepository.save(expected);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl()),
            () -> assertThat(actual.getCategory()).isEqualTo(expected.getCategory())
        );
    }

    @DisplayName("id로 상품 찾기")
    @Test
    void findById() {
        // given
        Product expected = productRepository.save(product);

        // when
        Product actual = productRepository.findById(expected.getId())
            .orElseThrow(NoSuchProductException::new);

        // then
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl()),
            () -> assertThat(actual.getCategory()).isEqualTo(expected.getCategory())
        );
    }

    @DisplayName("상품 수정")
    @Test
    void update() {
        // given
        long id = productRepository.save(product).getId();
        Product expected = new Product(id, "아이스 아메리카노", 5500, "image", category);

        // when
        Product actual = productRepository.save(expected);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl()),
            () -> assertThat(actual.getCategory()).isEqualTo(expected.getCategory())
        );
    }

    @DisplayName("상품 삭제")
    @Test
    void delete() {
        // given
        long id = productRepository.save(product).getId();

        // when
        productRepository.deleteById(id);

        // then
        assertThat(productRepository.findById(id)).isEmpty();
    }
}
