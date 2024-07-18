package gift.entity;

import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("상품 엔티티 테스트")
class ProductTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @DisplayName("Name Null")
    void nameNull() {
        Category category = categoryRepository.getReferenceById(1L);
        List<Option> options = List.of(new Option("option", 1010));
        Product product = new Product(null, 101, "img", category, options);

        assertThatThrownBy(() -> productRepository.save(product))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("이름 16자 일때 예외 발생")
    void nameLength() {
        Category category = categoryRepository.getReferenceById(1L);
        List<Option> options = List.of(new Option("option", 1010));
        Product product = new Product("1234".repeat(4), 101, "img", category, options);

        assertThatThrownBy(() -> productRepository.save(product))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
