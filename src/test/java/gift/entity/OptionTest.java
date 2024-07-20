package gift.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OptionTest {

    @Test
    void option_quantity_subtract_test() {
        // given
        Option option = new Option(new OptionDTO("test", 100));

        // when
        option.subtract(30);

        // then
        assertThat(option.getQuantity()).isEqualTo(70);
    }
}
