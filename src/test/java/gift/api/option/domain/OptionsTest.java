package gift.api.option.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

import gift.api.option.InvalidNameException;
import gift.api.product.Product;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OptionsTest {

    @Test
    @DisplayName(value = "중복된_이름의_옵션_추가_테스트")
    void validate() {
        // given
        var product = mock(Product.class);
        var name = "option";
        var quantity = 500;
        Option option = new Option(product, name, quantity);
        Options options = new Options(List.of(option));

        // when
        // then
        assertThatExceptionOfType(InvalidNameException.class)
            .isThrownBy(() -> options.validate(new Option(product, name, quantity)));
    }
}