package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import gift.product.dto.CategoryDto;
import gift.product.model.Category;
import gift.product.model.Product;
import gift.product.repository.CategoryRepository;
import gift.product.repository.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void 상품_추가() {
        Category category = categoryRepository.save(new Category("테스트카테고리1"));

        Product insertedProduct = productRepository.save(new Product("테스트1", 1500, "테스트주소1", category));

        assertSoftly(softly -> {
            assertThat(insertedProduct.getId()).isNotNull();
            assertThat(insertedProduct.getName()).isEqualTo("테스트1");
            assertThat(insertedProduct.getPrice()).isEqualTo(1500);
            assertThat(insertedProduct.getImageUrl()).isEqualTo("테스트주소1");
            assertThat(insertedProduct.getCategory().getName()).isEqualTo("테스트카테고리1");
        });
    }

    @Test
    void 상품_전체_조회() {
        Category category = categoryRepository.save(new Category("테스트카테고리1"));

        productRepository.save(new Product("테스트1", 1500, "테스트주소1", category));
        productRepository.save(new Product("테스트2", 3000, "테스트주소2", category));

        List<Product> products = productRepository.findAll();

        assertThat(products).hasSize(2);
    }

    @Test
    void 상품_조회() {
        Category category = categoryRepository.save(new Category("테스트카테고리1"));
        Product product = productRepository.save(new Product("테스트1", 1500, "테스트주소1", category));

        boolean isPresentProduct = productRepository.findById(product.getId()).isPresent();

        assertThat(isPresentProduct).isTrue();
    }

    @Test
    void 상품_수정() {
        Category category = categoryRepository.save(new Category("테스트카테고리1"));
        Product product = productRepository.save(new Product("테스트1", 1500, "테스트주소1", category));

        Product updatedProduct = productRepository.save(
            new Product(product.getId(), product.getName(), 9999,
                product.getImageUrl(), category));

        assertThat(updatedProduct.getPrice()).isEqualTo(9999);
    }

    @Test
    void 상품_삭제() {
        Category category = categoryRepository.save(new Category("테스트카테고리1"));
        Product product = productRepository.save(new Product("테스트1", 1500, "테스트주소1", category));

        productRepository.deleteById(product.getId());

        boolean isPresentProduct = productRepository.findById(product.getId()).isPresent();
        assertThat(isPresentProduct).isFalse();
    }
}
