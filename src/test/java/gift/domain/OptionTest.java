package gift.domain;

import static gift.util.CategoryFixture.createCategory;
import static gift.util.OptionFixture.createOption;
import static gift.util.ProductFixture.createProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import gift.exception.InsufficientQuantityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OptionTest {

    @DisplayName("옵션 수량 차감")
    @Test
    void subtract() {
        // given
        int quantity = 3;
        int subtractedQuantity = 1;
        Option option = createOption(1L, "test", quantity, createProduct(createCategory()));

        // when
        option.subtract(subtractedQuantity);

        // then
        assertThat(option.getQuantity()).isEqualTo(quantity - subtractedQuantity);
    }

    @DisplayName("옵션 수량 초과 차감")
    @Test
    void subtractOverQuantity() {
        // given
        int quantity = 3;
        int subtractedQuantity = 4;
        Option option = createOption(1L, "test", quantity, createProduct(createCategory()));

        // when
        // then
        assertThatExceptionOfType(InsufficientQuantityException.class)
            .isThrownBy(() -> option.subtract(subtractedQuantity));
    }
}
