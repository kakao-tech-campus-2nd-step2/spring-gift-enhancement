package gift.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import gift.common.exception.OptionException;
import gift.option.model.Option;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class OptionTest {

    @Test
    void updateInfo() {
        Option option = new Option("test", 1, null);
        String name = "changed";
        Integer quantity = 2;
        option.updateInfo(name, quantity);

        assertThat(option.getName()).isEqualTo(name);
        assertThat(option.getQuantity()).isEqualTo(quantity);
    }

    @Test
    void validateOptionCount() {
        assertThatExceptionOfType(OptionException.class).isThrownBy(
            () -> Option.Validator.validateOptionCount(List.of(new Option("test", 1, null)))
        );
    }

    @Test
    void validateDuplicated() {
        List<Option> options = new ArrayList<>();
        options.add(new Option("test", 1, null));

        assertThatExceptionOfType(OptionException.class).isThrownBy(
            () -> Option.Validator.validateName(options, new Option("test", 2, null))
        );
    }
}
