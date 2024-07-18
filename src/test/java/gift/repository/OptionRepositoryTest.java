package gift.repository;

import gift.config.JpaConfig;
import gift.model.Category;
import gift.model.Option;
import gift.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaConfig.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OptionRepositoryTest {
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("id로 Product와 Fetch Join하여 조회 테스트[성공]")
    void findByIdFetchJoin() {
        // given
        String pName = "pName";
        int pPrice = 10;
        String pImageUrl = "pImageUrl";
        Category category = categoryRepository.save(new Category("cname", "ccolor", "cImage", ""));
        String oName = "oName";
        int oQuantity = 123;
        List<Option> options = List.of(new Option(oName, oQuantity));
        Product product = productRepository.save(
                new Product(pName, pPrice, pImageUrl, category, options));
        options.get(0).updateOptionByProduct(product);

        // when
        Option actual = optionRepository.findByIdFetchJoin(options.get(0).getId()).orElse(null);
//
        // then
        assert actual != null;
        assertThat(actual.getName()).isEqualTo(oName);
        assertThat(actual.getQuantity()).isEqualTo(oQuantity);
        assertThat(actual.getProduct()).isEqualTo(product);
    }

    @Test
    @DisplayName(" productId로 Product와 Fetch Join하여 조회 테스트[성공]")
    void findAllByProductIdFetchJoin() {
        // given
        String pName = "pName";
        int pPrice = 10;
        String pImageUrl = "pImageUrl";
        Category category = categoryRepository.save(new Category("cname", "ccolor", "cImage", ""));
        String oName = "oName";
        int oQuantity = 123;
        List<Option> options = List.of(new Option(oName, oQuantity));
        Product product = productRepository.save(new Product(pName, pPrice, pImageUrl, category, options));
        options.get(0).updateOptionByProduct(product);

        // when
        List<Option> actual = optionRepository.findAllByProductIdFetchJoin(product.getId());

        // then
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0)).isEqualTo(options.get(0));
    }
}