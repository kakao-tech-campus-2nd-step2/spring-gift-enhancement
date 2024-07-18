package gift;

import gift.entity.Category;
import gift.entity.Product;
import gift.repository.CategoryRepositoryInterface;
import gift.repository.ProductRepositoryInterface;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DataJpaTest
public class ProductRepositoryTest {

    ProductRepositoryInterface products;
    CategoryRepositoryInterface categorys;

    @Test
    void save() {
        Category category = categorys.findCategoryByName("식품");
        final Product product = new Product("사과", 500, "naver.com", category);
        assertThat(product.getId()).isNull();
        var actualProduct = products.save(product);
        assertThat(actualProduct.getId()).isNotNull();
    }

    @Test
    void findByName() {
        Category category = categorys.findCategoryByName("과일");
        products.save(new Product("사과", 500, "naver.com", category ));
        final Product actualProduct = products.findByName("사과");
        assertThat(actualProduct.getId()).isNotNull();
        assertThat(actualProduct.getName()).isEqualTo("사과");
    }
}
