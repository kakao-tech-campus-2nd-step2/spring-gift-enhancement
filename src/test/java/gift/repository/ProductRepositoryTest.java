package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.category.CategoryRepository;
import gift.category.model.Category;
import gift.product.ProductRepository;
import gift.product.model.Product;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    private Category category;

    @BeforeEach
    void setup() throws Exception {
        if (categoryRepository.findAll().isEmpty()) {
            category = new Category(
                "test",
                "##test",
                "test.jpg",
                "test"
            );
            categoryRepository.save(category);
        }
    }

    @Test
    void findAll() {
        Product expected1 = new Product("kimchi", 5000, "kimchi.jpg", category);
        Product expected2 = new Product("computer", 2000000, "computer.jpg", category);
        productRepository.save(expected1);
        productRepository.save(expected2);

        List<Product> actual = productRepository.findAll(
            PageRequest.of(0, 10, Sort.by(Direction.ASC, "id"))).getContent();
        assertThat(actual).containsExactly(expected1, expected2);
    }
}
