package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.category.Category;
import gift.category.CategoryRepository;
import gift.product.Product;
import gift.product.ProductRepository;
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

        //When
        Optional<Product> actual = productRepository.findById(product.getId());
        assertThat(actual).isPresent();

        //Then
        assertThat(actual.get().getName()).isEqualTo("라이언");
        assertThat(actual.get().getPrice()).isEqualTo(1000);
        assertThat(actual.get().getImageUrl()).isEqualTo("image.jpg");
        assertThat(actual.get().getCategory()).isEqualTo(category);
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
        assertThat(actual.getFirst().getPrice()).isEqualTo(1000);
        assertThat(actual.getFirst().getName()).isEqualTo("라이언");
        assertThat(actual.getFirst().getImageUrl()).isEqualTo("image.jpg");
        assertThat(actual.getFirst().getCategory()).isEqualTo(category);
        assertThat(actual.get(1).getPrice()).isEqualTo(3000);
        assertThat(actual.get(1).getName()).isEqualTo("이춘식");
        assertThat(actual.get(1).getImageUrl()).isEqualTo("example.jpg");
        assertThat(actual.get(1).getCategory()).isEqualTo(category);
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
