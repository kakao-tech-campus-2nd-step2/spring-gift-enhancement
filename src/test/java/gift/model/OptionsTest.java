package gift.model;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import gift.common.exception.OptionException;
import gift.option.model.Option;
import gift.option.model.Options;
import java.util.List;
import org.junit.jupiter.api.Test;

class OptionsTest {

    @Test
    void validateDuplicated() {
        Options options = new Options(List.of(new Option("option", 1, null)));
        assertThatExceptionOfType(OptionException.class).isThrownBy(
            () -> options.validateDuplicated(new Option("option", 2, null)));
    }

    @Test
    void validateOptionSize() {
        Options options = new Options(List.of(new Option("option", 1, null)));
        assertThatExceptionOfType(OptionException.class).isThrownBy(options::validateOptionSize);
    }
}
