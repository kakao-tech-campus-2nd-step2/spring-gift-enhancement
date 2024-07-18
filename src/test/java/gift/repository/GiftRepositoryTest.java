package gift.repository;

import gift.model.category.Category;
import gift.model.gift.Gift;
import gift.model.option.Option;
import gift.repository.gift.GiftRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class GiftRepositoryTest {

    @Autowired
    private GiftRepository giftRepository;

    @Test
    void findAllTest() {
        Gift gift = giftRepository.findById(1L).orElseThrow(IllegalArgumentException::new);
        assertThat(gift.getName()).isEqualTo("coffe");
    }

    @Test
    void saveTest() {
        Category category = new Category(10L, "test", "test", "test", "test");
        Option option1 = new Option("testOption", 1);
        List<Option> option = Arrays.asList(option1);
        Gift gift = new Gift("test", 1000, "abc.jpg", category, option);
        Gift actual = giftRepository.save(gift);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(gift.getName())
        );
    }


}