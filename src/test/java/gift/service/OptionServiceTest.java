package gift.service;

import gift.entity.Option;
import gift.entity.OptionDTO;
import gift.entity.Product;
import gift.entity.ProductDTO;
import gift.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class OptionServiceTest {
    @Autowired
    private OptionService optionService;
    @Autowired
    private ProductService productService;

    @Test
    void save() {
        // given
        OptionDTO actual = new OptionDTO("abc", 123);

        // when
        Option expect = optionService.save(actual);

        // then
        assertThat(expect).isNotNull();
    }

    @Test
    void findById() {
        // given
        OptionDTO actual = new OptionDTO("abc", 123);
        Option saved = optionService.save(actual);

        // when
        Option expect = optionService.findById(saved.getId());

        // then
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expect.getName()),
                () -> assertThat(actual.getQuantity()).isEqualTo(expect.getQuantity())
        );
    }

    @Test
    void update() {
        // given
        OptionDTO option = new OptionDTO("abc", 123);
        OptionDTO actual = new OptionDTO("def", 456);
        Option saved = optionService.save(option);

        // when
        Option expect = optionService.update(saved.getId(), actual);

        // then
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expect.getName()),
                () -> assertThat(actual.getQuantity()).isEqualTo(expect.getQuantity())
        );
    }

    @Test
    void delete() {
        // given
        OptionDTO actual = new OptionDTO("abc", 123);
        Option saved = optionService.save(actual);

        // when
        optionService.delete(saved.getId());

        // then
        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> optionService.findById(saved.getId()));
        assertThat(exception.getMessage())
                .isEqualTo("Option not found with id: " + saved.getId());
    }

    @Test
    void subtract_실패() {
        // given
        OptionDTO actual = new OptionDTO("abc", 10);
        Option saved = optionService.save(actual);

        // when
        // then
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> optionService.subtract(saved.getId(), 100));
        assertThat(exception.getMessage()).isEqualTo("Not enough quantity");
    }

    @Test
    void subtract_성공() {
        // given
        OptionDTO actual = new OptionDTO("abc", 10);
        Option saved = optionService.save(actual);

        // when
        optionService.subtract(saved.getId(), 7);
        Option expect = optionService.findById(saved.getId());

        // then
        assertThat(expect.getQuantity()).isEqualTo(3);
    }

    @Test
    void 같은_이름의_option이_product에_있을_때() {
        // given
        Product product = productService.save(new ProductDTO("test", 123, "test.com", 1L));
        Option option1 = optionService.save(new OptionDTO("test", 123));
        Option option2 = optionService.save(new OptionDTO("test", 456));

        // when
        productService.addProductOption(product.getId(), option1.getId());

        // then
        assertThrows(DataIntegrityViolationException.class,
                () -> productService.addProductOption(product.getId(), option2.getId()));
    }
}
