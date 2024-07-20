package gift.category.service;

import gift.category.model.Category;

import gift.category.repository.CategoryRepository;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        categoryRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void 카테고리_생성_성공여부() {
        // Given
        Category category1 = new Category("Adidas", "black", "https://www.google.com");
        categoryRepository.save(category1);

        Product product = new Product("Product1", 35000, "https://www.google.com", category1);
        productRepository.save(product);

        // When
        Product foundProduct = productRepository.findById(product.getId()).orElse(null);

        // Then
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getCategory()).isEqualTo(category1);
    }
}