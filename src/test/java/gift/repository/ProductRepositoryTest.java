package gift.repository;

import gift.entity.Category;
import gift.entity.Option;
import gift.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("상품 레포지토리 단위테스트")
class ProductRepositoryTest {

    private final Category testCategory = new Category("교환권", "테스트", "테스트", "테스트");
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        testEntityManager.persist(testCategory);
    }

    @Test
    @DisplayName("상품 저장")
    void saveTest() {
        // Given
        Product product = new Product("아몬드", 500, "image.jpg", testCategory,List.of(new Option("option1",1)));
        Long savedProductId = productRepository.save(product).getId();

        // When
        Optional<Product> foundProduct = productRepository.findById(savedProductId);

        // Then
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("아몬드");
    }

    @Test
    @DisplayName("상품 읽기(read)")
    void readTest() {
        // Given
        Product product1 = new Product("아몬드", 500, "image.jpg", testCategory,List.of(new Option("option1",1)));
        Product product2 = new Product("초코", 5400, "image2.jpg", testCategory,List.of(new Option("option1",1)));
        productRepository.save(product1);
        productRepository.save(product2);

        // When
        List<Product> foundProducts = productRepository.findAll();

        // Then
        assertThat(foundProducts).hasSize(2);
        assertThat(foundProducts).containsExactly(product1, product2);
    }

    @Test
    @DisplayName("상품 수정")
    void updateTest() {
        // Given
        Product product = new Product("아몬드", 500, "image.jpg", testCategory,List.of(new Option("option1",1)));
        Product savedProduct = productRepository.save(product);

        // When
        savedProduct.update("아몬드봉봉", 600, "image.jpg", testCategory);

        // Then
        assertThat(savedProduct.getName()).isEqualTo("아몬드봉봉");
    }

    @Test
    @DisplayName("상품 삭제")
    void deleteTest() {
        // Given
        Product product = new Product("아몬드", 500, "image.jpg", testCategory,List.of(new Option("option1",1)));
        Product savedProduct = productRepository.save(product);
        Long savedProductId = savedProduct.getId();

        // When
        productRepository.delete(savedProduct);
        Optional<Product> deleteResult = productRepository.findById(savedProductId);

        // Then
        assertThat(deleteResult).isNotPresent();
    }
}
