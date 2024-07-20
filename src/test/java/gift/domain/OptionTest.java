package gift.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import gift.domain.option.entity.Option;
import gift.domain.option.exception.OptionNameDuplicateException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OptionTest {

    @Test
    @DisplayName("이름이 중복일 경우 테스트")
    void checkDuplicateNameTest() {
        List<Option> optionList = Arrays.asList(createOption(), createOption("test2"));
        Option newOption = createOption("test1");

        assertThatThrownBy(() -> newOption.checkDuplicateName(optionList)).isInstanceOf(
            OptionNameDuplicateException.class);
    }

    @Test
    @DisplayName("이름이 중복이 아닐 경우 테스트")
    void checkNotDuplicateNameTest() {
        List<Option> optionList = Arrays.asList(createOption(), createOption("test2"));
        Option newOption = createOption("test3");

        assertDoesNotThrow(() -> newOption.checkDuplicateName(optionList));
    }

    Option createOption() {
        return createOption("test1");
    }

    Option createOption(String name) {
        return new Option(name, 1000);
    }
}
