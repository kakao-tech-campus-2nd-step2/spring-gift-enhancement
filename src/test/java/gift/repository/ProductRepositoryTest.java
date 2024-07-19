package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.model.Category;
import gift.model.Option;
import gift.model.Product;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Test
    @DisplayName("상품 저장 및 ID로 조회")
    public void testSaveAndFindById() {
        Category category = new Category("Test Category", "#000000", "imageUrl", "description");
        categoryRepository.save(category);

        Product tempProduct = new Product();

        Option option1 = new Option(null, "Option1", 100, tempProduct);
        Option option2 = new Option(null, "Option2", 200, tempProduct);

        Product product = new Product(null, "Test Product", 100, "test.jpg", category, List.of(option1, option2));

        option1.setProduct(product);
        option2.setProduct(product);

        Product savedProduct = productRepository.save(product);
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());

        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Test Product");
        assertThat(foundProduct.get().getCategoryName()).isEqualTo("Test Category");
        assertThat(foundProduct.get().getOptions()).hasSize(2);
    }

    @Test
    @DisplayName("모든 상품 조회")
    public void testFindAll() {
        long initialCount = productRepository.count();

        Category category = new Category("Test Category", "#000000", "imageUrl", "description");
        categoryRepository.save(category);

        Product tempProduct1 = new Product();
        Product tempProduct2 = new Product();

        Option option1 = new Option(null, "Option1", 100, tempProduct1);
        Option option2 = new Option(null, "Option2", 200, tempProduct1);
        Option option3 = new Option(null, "Option3", 300, tempProduct2);
        Option option4 = new Option(null, "Option4", 400, tempProduct2);

        Product product1 = new Product(null, "Product 1", 100, "prod1.jpg", category, List.of(option1, option2));
        Product product2 = new Product(null, "Product 2", 200, "prod2.jpg", category, List.of(option3, option4));

        option1.setProduct(product1);
        option2.setProduct(product1);
        option3.setProduct(product2);
        option4.setProduct(product2);

        productRepository.save(product1);
        productRepository.save(product2);

        Iterable<Product> products = productRepository.findAll();
        assertThat(products).hasSize((int) initialCount + 2);
    }

    @Test
    @DisplayName("상품 삭제")
    public void testDelete() {
        Category category = new Category("Test Category", "#000000", "imageUrl", "description");
        categoryRepository.save(category);

        Product tempProduct = new Product();

        Option option1 = new Option(null, "Option1", 100, tempProduct);
        Option option2 = new Option(null, "Option2", 200, tempProduct);

        Product product = new Product(null, "Test Product", 100, "test.jpg", category, List.of(option1, option2));

        option1.setProduct(product);
        option2.setProduct(product);

        Product savedProduct = productRepository.save(product);

        productRepository.deleteById(savedProduct.getId());
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());

        assertThat(foundProduct).isNotPresent();
    }
}
