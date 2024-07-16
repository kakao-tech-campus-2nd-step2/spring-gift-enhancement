package gift.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import gift.model.Category;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    private Category category;
    private Product product;
    
    @BeforeEach
    void setUp() {
    	category = new Category("교환권", "#6c95d1", "https://example.com/image.jpg", "");
    	categoryRepository.save(category);
    	product = new Product("아이스 아메리카노 T", 4500, "https://example.com/image.jpg", category);
    }
    
    @Test
    void save() {
        Product actual = productRepository.save(product);
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo(product.getName());
    }

    @Test
    void findById() {
    	productRepository.save(product);
        Product actual = productRepository.findById(product.getId()).orElse(null);
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(product.getName());
    }
}