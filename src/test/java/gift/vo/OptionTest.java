package gift.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class OptionTest {

    @Test
    @DisplayName("Test for Option Name Exceeding 50 Characters")
    void Exceed50Characters() {
        // given
        Product mockProduct = Mockito.mock(Product.class);
        String falseName = "옵션명".repeat(50);

        // when
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy( () -> new Option(null, mockProduct, falseName, 500));
    }


}