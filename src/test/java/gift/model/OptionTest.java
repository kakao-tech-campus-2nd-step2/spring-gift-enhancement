package gift.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import gift.common.exception.OptionException;
import gift.option.model.Option;
import org.junit.jupiter.api.Test;

class OptionTest {

    @Test
    void validateDuplicated() {
        Option option = new Option("test", 1, null);

        assertThatExceptionOfType(OptionException.class).isThrownBy(
            () -> option.validateDuplicated(new Option("test", 1, null)));
    }

    @Test
    void updateInfo() {
        Option option = new Option("test", 1, null);
        String name = "changed";
        Integer quantity = 2;
        option.updateInfo(name, quantity);

        assertThat(option.getName()).isEqualTo(name);
        assertThat(option.getQuantity()).isEqualTo(quantity);
    }
}
