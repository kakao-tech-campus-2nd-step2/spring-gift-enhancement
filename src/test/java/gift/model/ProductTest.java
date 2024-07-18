package gift.model;

import gift.common.exception.DuplicateDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProductTest {

    @Test
    @DisplayName("옵션명 중복 체크[실패] - 중복된 이름")
    void checkDuplicateName() {
        // given
        Category category = new Category();
        Option option = new Option("oName", 100);
        Option option2 = new Option("oName", 1100);
        Product product = new Product("pname", 1000, "purl", category, option);

        // when
        // then
        assertThatExceptionOfType(DuplicateDataException.class)
                .isThrownBy(() -> product.addOption(option2));
    }
}