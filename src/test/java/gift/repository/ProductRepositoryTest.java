package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.administrator.category.Category;
import gift.administrator.category.CategoryRepository;
import gift.administrator.product.Product;
import gift.administrator.product.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private Category category;
    private Product product;

    @BeforeEach
    void beforeEach() {
        category = new Category("상품권", null, null, null);
        categoryRepository.save(category);
        product = new Product("라이언", 1000, "image.jpg", category);
    }

    @Test
    @DisplayName("상품 추가 테스트")
    void save() {
        //Given

        //When
        Product actual = productRepository.save(product);

        //Then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo("라이언");
        assertThat(actual.getPrice()).isEqualTo(1000);
        assertThat(actual.getImageUrl()).isEqualTo("image.jpg");
        assertThat(actual.getCategory()).isEqualTo(category);
    }

    @Test
    @DisplayName("상품 아이디로 찾기 테스트")
    void findById() {
        //Given
        productRepository.save(product);
        Product expected = new Product("라이언", 1000, "image.jpg", category);

        //When
        Optional<Product> actual = productRepository.findById(product.getId());

        //Then
        assertThat(actual).isPresent();
        assertThat(actual.get())
            .extracting(Product::getName, Product::getPrice, Product::getImageUrl,
                Product::getCategory)
            .containsExactly(expected.getName(), expected.getPrice(), expected.getImageUrl(),
                expected.getCategory());
    }

    @Test
    @DisplayName("전체 상품 찾기 테스트")
    void findAll() {
        //Given
        productRepository.save(product);
        Product product2 = new Product("이춘식", 3000, "example.jpg", category);
        productRepository.save(product2);

        //When
        List<Product> actual = productRepository.findAll();

        //Then
        assertThat(actual).hasSize(2);
        assertThat(actual.getFirst().getId()).isNotNull();
        assertThat(actual.get(1).getId()).isNotNull();
        assertThat(actual)
            .containsExactly(product, product2);
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteById() {
        //Given
        productRepository.save(product);

        //When
        productRepository.deleteById(product.getId());
        Optional<Product> actual = productRepository.findById(product.getId());

        //Then
        assertThat(actual).isNotPresent();
    }
}
