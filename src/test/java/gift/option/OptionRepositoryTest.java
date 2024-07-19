package gift.option;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OptionRepositoryTest {

    @Autowired
    private OptionRepository optionRepository;

    @Test
    void save() {
        //given
        Option expected = new Option(null, "option", 1, 1L);

        //when
        Option actual = optionRepository.save(expected);

        //then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getQuantity()).isEqualTo(expected.getQuantity()),
            () -> assertThat(actual.getProductId()).isEqualTo(expected.getProductId())
        );
    }

    @Test
    void deleteById() {
        //given
        Long id = 1L;
        Option expected = new Option(id, "option", 1, 1L);
        Option option = optionRepository.save(expected);

        //when
        optionRepository.deleteById(option.getId());

        //then
        assertThat(optionRepository.findById(id)).isEmpty();
    }

    @Test
    void findAllByProductId() {
        //given
        Long id = 1L;
        for(int i = 0; i<5;i++){
            Option option = new Option(null, "option" + i, 1, id);
            optionRepository.save(option);
        }

        //when
        List<Option> options = optionRepository.findAllByProductId(id);

        //then
        assertAll(
            () -> assertThat(options.size()).isEqualTo(5),
            () -> assertThat(options.get(0).getName()).isEqualTo("option0"),
            () -> assertThat(options.get(3).getName()).isEqualTo("option3"),
            () -> assertThat(options.get(3).getId()).isEqualTo(4),
            () -> assertThat(options.get(4).getProductId()).isEqualTo(id)
        );

    }
}