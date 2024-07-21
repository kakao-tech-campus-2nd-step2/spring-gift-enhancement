package gift.api.option.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import gift.api.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OptionTest {

    @Test
    @DisplayName(value = "정상_옵션_수량_차감_테스트")
    void subtract() {
        // given
        var before = 100;
        var quantity = 57;
        var product = mock(Product.class);
        var option = new Option(product, "name", before);

        // when
        option.subtract(quantity);

        // then
        assertThat(option.getQuantity())
            .isEqualTo(before - quantity);
    }
}