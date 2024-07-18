package gift.category.service;

import gift.Application;
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

@SpringBootTest(classes = Application.class)
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
    public void 한_상품이_카테고리_여러개를_가지는_경우() {
        // Given
        Category category1 = new Category("Adidas", "black", "https://www.google.com");
        Category category2 = new Category("Pizze", "white", "https://www.google.com");
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        Product product = new Product("Product1", 35000, "https://www.google.com", category1);
        productRepository.save(product);

        // When
        Product updatedProduct = new Product(product.getName(), product.getPrice(), product.getImgUrl(), category2);
        productRepository.save(updatedProduct);
        Product foundProduct = productRepository.findById(product.getProductId()).orElse(null);

        // Then
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getCategory()).isEqualTo(category2);
    }
}
