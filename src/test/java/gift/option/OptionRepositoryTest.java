package gift.option;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.product.Product;
import gift.product.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Transactional
class OptionRepositoryTest {

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        //given
        Product product = productRepository.save(product());
        Option expected = new Option(null, "option", 1, product);

        //when
        Option actual = optionRepository.save(expected);

        //then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getQuantity()).isEqualTo(expected.getQuantity()),
            () -> assertThat(actual.getProduct()).isEqualTo(expected.getProduct())
        );
    }

    @Test
    void deleteById() {
        //given
        Long id = 1L;
        Product product = productRepository.save(product());
        Option expected = new Option(id, "option", 1, product);
        Option option = optionRepository.save(expected);

        //when
        optionRepository.deleteById(option.getId());

        //then
        assertThat(optionRepository.findById(id)).isEmpty();
    }

    @Test
    void findAllByProductId() {
        //given
        Product product = productRepository.save(product());
        productRepository.flush();
        for(int i = 0; i<5;i++){
            Option option = new Option(null, "option" + i, 1, product);
            optionRepository.save(option);
        }
        productRepository.flush();

        //when
        List<Option> options = optionRepository.findAllByProductId(product.getId());

        //then
        assertAll(
            () -> assertThat(options.size()).isEqualTo(5),
            () -> assertThat(options.get(0).getName()).isEqualTo("option0"),
            () -> assertThat(options.get(3).getName()).isEqualTo("option3"),
            () -> assertThat(options.get(3).getId()).isEqualTo(4),
            () -> assertThat(options.get(4).getProduct()).isEqualTo(product)
        );

    }

    private Product product() {
        return new Product(1L, "product", 1, "image", 1L);
    }

}