package gift.domain.product.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import gift.exception.OutOfStockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OptionTest {

    @Test
    @DisplayName("옵션 수량 차감 성공")
    void subtract_success() {
        // given
        Category category = new Category(1L, "교환권", "#FFFFFF", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", "test");
        Product product = new Product(1L, category, "testProduct", 10000, "https://test.com");
        Option option = new Option(1L, product, "사과맛", 70);

        // when
        option.subtract(7);

        // then
        assertThat(option.getQuantity()).isEqualTo(63);
    }

    @Test
    @DisplayName("옵션 수량 차감 실패")
    void subtract_fail() {
        // given
        Category category = new Category(1L, "교환권", "#FFFFFF", "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", "test");
        Product product = new Product(1L, category, "testProduct", 10000, "https://test.com");
        Option option = new Option(1L, product, "사과맛", 70);

        // when & then
        assertThatThrownBy(() -> option.subtract(80))
            .isInstanceOf(OutOfStockException.class);
    }
}