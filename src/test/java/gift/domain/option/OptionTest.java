package gift.domain.option;

import gift.domain.category.Category;
import gift.domain.product.Product;
import gift.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OptionTest {
    @Test
    void 옵션_차감_성공() {
        //given
        Product product = new Product("더미", 10000, "test.jpg",
                new Category("테스트", "##", "설명", "test"));
        Option option = new Option("테스트", 100, product);
        //when
        option.subtract(70);
        //then
        Assertions.assertThat(option.getQuantity()).isEqualTo(30);
    }

    @Test
    void 옵션_차감_실패() {
        //given
        Product product = new Product("더미", 10000, "test.jpg",
                new Category("테스트", "##", "설명", "test"));
        Option option = new Option("테스트", 100, product);
        //when
        //then
        Assertions.assertThatExceptionOfType(CustomException.class)
                .isThrownBy(() -> option.subtract(110));
    }
}
