package gift.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OptionTest {

    @Test
    void option_quantity_subtract_test() {
        // given
        Option option = new Option(new OptionDTO("test", 100));

        // when
        option.subtract(30);

        // then
        Assertions.assertThat(option.getQuantity()).isEqualTo(70);
    }
}
