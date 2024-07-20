package gift.domain;

import gift.common.exception.AlreadyExistName;
import gift.model.category.Category;
import gift.model.option.Option;
import gift.model.product.Product;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OptionTest {

    @DisplayName("옵션 생성 테스트")
    @Test
    void create() {
        // given
        String name = "Option1";
        int quantity = 100;

        // when
        Option option = createOption(name, quantity, null);

        // then
        Assertions.assertThat(option.getName()).isEqualTo(name);
        Assertions.assertThat(option.getQuantity()).isEqualTo(quantity);
    }

    @Test
    @DisplayName("옵션 수량 감소 시 테스트")
    void subtractOption() {
        // given
        Option option = createOption("Option", 100, null);

        // when
        option.subtract(option, 50);

        // then
        Assertions.assertThat(option.getQuantity()).isEqualTo(50);
    }

    @Test
    @DisplayName("옵션 수량 감소 시 예외 발생 테스트")
    void subtractException() {
        // given
        Option option = createOption("Option", 100, null);

        // when
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
            () -> option.subtract(option, 150));
    }




    private Product createProduct(String name, int price, String url, Category category, List<Option> options) {
        return new Product(name, price, url, category, options);
    }

    private Category createCategory(String name, String color, String imageUrl, String description) {
        return new Category(name, color, imageUrl, description);
    }

    private Option createOption(String name, int quantity, Product product) {
        return new Option(name, quantity, product);
    }

}
