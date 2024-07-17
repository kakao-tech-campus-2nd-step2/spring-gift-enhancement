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
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaConfig.class)
@ActiveProfiles("test")
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
        Product product = productRepository.save(
                new Product(pName, pPrice, pImageUrl, category, new Option(oName, oQuantity)));

        // when
        Option actual = optionRepository.findByIdFetchJoin(product.getOptions().get(0).getId()).get();

        // then
        assertThat(actual.getId()).isEqualTo(product.getOptions().get(0).getId());
        assertThat(actual.getName()).isEqualTo(product.getOptions().get(0).getName());
        assertThat(actual.getQuantity()).isEqualTo(product.getOptions().get(0).getQuantity());
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
        Option option = new Option(oName, oQuantity);
        Product product = new Product(pName, pPrice, pImageUrl, category, option);
        Product saved = productRepository.save(product);

        // when
        List<Option> actual = optionRepository.findAllByProductIdFetchJoin(saved.getId());

        // then
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0)).isEqualTo(option);
    }
}