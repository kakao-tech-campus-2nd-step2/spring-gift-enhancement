package gift.repository;

import gift.config.JpaConfig;
import gift.model.Category;
import gift.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaConfig.class)
@ActiveProfiles("test")
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("id로 상품 정보 업데이트 테스트[성공]")
    void updateById() {
        // given
        String name = "name1";
        int price = 1000;
        String imageUrl = "imageUrl1";
        String imageUrl2 = "imageUrl2";
        Category category = categoryRepository.save(new Category("name", "#123", "url", ""));
        Product product = new Product(name, price, imageUrl, category);
        Product original = productRepository.save(product);

        // when
        Product updated = productRepository.findById(original.getId()).orElse(null);
        assert updated != null;
        updated.updateProduct(name, price, imageUrl2, category);
        Product actual = productRepository.save(updated);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getPrice()).isEqualTo(price);
        assertThat(actual.getImageUrl()).isEqualTo(imageUrl2);
        assertThat(actual.getCreatedAt()).isNotNull();
        assertThat(actual.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("상품 정보 저장 테스트[성공]")
    void save() {
        // given
        String name = "name1";
        int price = 1000;
        String imageUrl = "imageUrl1";
        Category category = categoryRepository.save(new Category("name", "#123", "url", ""));
        Product product = new Product(name, price, imageUrl, category);

        // when
        Product actual = productRepository.save(product);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getPrice()).isEqualTo(price);
        assertThat(actual.getImageUrl()).isEqualTo(imageUrl);
        assertThat(actual.getCreatedAt()).isNotNull();
        assertThat(actual.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("카테고리와 fetch join하여 모든 상품 조회 테스트[성공]")
    void findAllFetchJoin() {
        // given
        String[] name = {"name1", "name2"};
        int price = 1000;
        String imageUrl = "imageUrl1";
        Category category = categoryRepository.save(new Category("name", "#123", "url", ""));
        Product[] product = {
                new Product(name[0], price, imageUrl, category),
                new Product(name[1], price, imageUrl, category)
        };
        Pageable pageable = PageRequest.of(0, 10);
        productRepository.save(product[0]);
        productRepository.save(product[1]);

        // when
        Page<Product> actuals = productRepository.findAllFetchJoin(pageable);

        // then
        assertThat(actuals.getTotalElements()).isEqualTo(2);
        assertThat(actuals.getTotalPages()).isEqualTo(1);
        assertThat(actuals.getNumber()).isEqualTo(0);
        for (int i = 0; i < actuals.getContent().size(); i++) {
            assertThat(actuals.getContent().get(i)).isEqualTo(product[i]);
        }
    }
}