package gift.product;

import gift.product.infrastructure.persistence.repository.JpaProductCategoryRepository;
import gift.product.infrastructure.persistence.repository.JpaProductRepository;
import gift.product.infrastructure.persistence.entity.ProductCategoryEntity;
import gift.product.infrastructure.persistence.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTests {
    @Autowired
    private JpaProductCategoryRepository jpaProductCategoryRepository;

    @Autowired
    private JpaProductRepository jpaProductRepository;

    private ProductEntity product;
    private ProductCategoryEntity category;

    @Test
    public void saveProduct() {
        ProductCategoryEntity category = jpaProductCategoryRepository.save(new ProductCategoryEntity("test"));
        ProductEntity product = jpaProductRepository.save(new ProductEntity("test", 100, "test", category));

        assertThat(jpaProductRepository.findById(product.getId())).isPresent();
        assertThat(jpaProductRepository.findById(product.getId()).get()).isEqualTo(product);
    }

    @Test
    public void findProductById() {
        ProductCategoryEntity category = jpaProductCategoryRepository.save(new ProductCategoryEntity("test"));
        ProductEntity product = jpaProductRepository.save(new ProductEntity("test", 100, "test", category));

        assertThat(jpaProductRepository.findById(product.getId())).isPresent();
    }

    @Test
    public void deleteProduct() {
        ProductCategoryEntity category = jpaProductCategoryRepository.save(new ProductCategoryEntity("test"));
        ProductEntity product = jpaProductRepository.save(new ProductEntity("test", 100, "test", category));

        jpaProductRepository.deleteById(product.getId());

        assertThat(jpaProductRepository.findById(product.getId())).isEmpty();
    }
}
