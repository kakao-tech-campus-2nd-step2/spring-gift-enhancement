package gift.vo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class OptionTest {

    Product mockProduct;

    @BeforeEach
    void setUp() {
        mockProduct = Mockito.mock(Product.class);
    }

    @Test
    @DisplayName("Test for Option Name Exceeding 50 Characters")
    void Exceed50Characters() {
        // given
        String falseName = "옵션명".repeat(50);

        // When & Then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Option(null, mockProduct, falseName, 500))
                .withMessage("옵션명은 공백 포함하여 최대 50자까지 가능합니다.");
    }
    
    @Test
    @DisplayName("Test for special characters excluding () [] + - & /")
    void CheckSpecialCharacters() {
        // given
        String falseName = "**특가**";

        // When & Then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy( () -> new Option(null, mockProduct, falseName, 500))
                .withMessage("상품명에 () [] + - & / 외의 특수기호는 불가합니다");
    }

}