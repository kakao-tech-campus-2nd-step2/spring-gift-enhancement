package gift.entity;

import gift.repository.OptionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("옵션 엔티티 테스트")
class OptionTest {

    @Autowired
    OptionRepository optionRepository;

    @Test
    @DisplayName("Name Null")
    void nameNull() {
        Option option = new Option(null, 100);

        assertThatThrownBy(() -> optionRepository.save(option))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Quantity Null")
    void quantityNull() {
        Option option = new Option("name", null);

        assertThatThrownBy(() -> optionRepository.save(option))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("정상적으로")
    void success() {
        Option option = new Option("name", 100);

        assertThat(optionRepository.save(option).getId()).isEqualTo(1L);
    }
}
