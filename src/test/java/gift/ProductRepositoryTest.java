package gift;

import gift.entity.Category;
import gift.entity.Option;
import gift.entity.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DataJpaTest
public class ProductRepositoryTest {

    ProductRepository products;
    CategoryRepository categorys;

    @Test
    void save() {
        Category category = categorys.findCategoryByName("식품");
        List<Option> options = new ArrayList<>();
        options.add(new Option("옵션1", 2L));
        final Product product = new Product("사과", 500, "naver.com", category, options);
        assertThat(product.getId()).isNull();
        var actualProduct = products.save(product);
        assertThat(actualProduct.getId()).isNotNull();
    }

    @Test
    void findByName() {
        Category category = categorys.findCategoryByName("과일");
        List<Option> options = new ArrayList<>();
        options.add(new Option("옵션1", 2L));
        products.save(new Product("사과", 500, "naver.com", category, options));
        final Product actualProduct = products.findByName("사과");
        assertThat(actualProduct.getId()).isNotNull();
        assertThat(actualProduct.getName()).isEqualTo("사과");
    }
}
