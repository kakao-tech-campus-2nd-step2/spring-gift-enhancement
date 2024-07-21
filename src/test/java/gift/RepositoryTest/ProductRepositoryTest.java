package gift.RepositoryTest;

import gift.Model.Category;
import gift.Model.Product;
import gift.Repository.CategoryRepository;
import gift.Repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    void beforeEach() {
        category = categoryRepository.save(new Category("카테고리", "#812f3D", "카테고리 url", "카테고리 description"));
    }

    @Test
    void saveTest(){
        Product product = new Product("아메리카노", 4000,"아메리카노url", category);
        assertThat(product.getId()).isNull();
        var actual = productRepository.save(product);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void findByIdTest(){
        Product product = productRepository.save(new Product("아메리카노", 4000, "아메리카노url", category));
        Optional<Product> actual = productRepository.findById(product.getId());
        assertThat(actual.get().getName()).isEqualTo("아메리카노");
    }

    @Test
    void updateTest(){
        Product product1 = productRepository.save(new Product("아메리카노", 4000, "아메리카노url", category));
        Optional<Product> optionalProduct = productRepository.findById(product1.getId());
        Product product = optionalProduct.get();
        product.setName("카푸치노");

        var actual = productRepository.findById(product.getId());
        assertThat(actual.get().getName()).isEqualTo("카푸치노");
    }

    @Test
    void deleteTest(){
        Product product1 = productRepository.save(new Product("아메리카노", 4000, "아메리카노url", category));
        Optional<Product> optionalProduct = productRepository.findById(product1.getId());
        productRepository.deleteById(optionalProduct.get().getId());
        Optional<Product> actual  = productRepository.findById(optionalProduct.get().getId());
        assertThat(actual).isEmpty();
    }
}
