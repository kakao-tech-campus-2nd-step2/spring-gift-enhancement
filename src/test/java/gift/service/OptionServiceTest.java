package gift.service;

import gift.entity.Option;
import gift.entity.OptionDTO;
import gift.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class OptionServiceTest {
    @Autowired
    private OptionService optionService;

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
        assertThrows(ResourceNotFoundException.class,
                () -> optionService.findById(saved.getId()));
    }
}
