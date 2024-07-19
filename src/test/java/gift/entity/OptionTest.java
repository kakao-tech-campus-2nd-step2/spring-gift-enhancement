package gift.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OptionTest {

    @Test
    void optionConstructorAndGetters() {
        Product product = new Product();
        Option option = new Option("Test Option", 100, product);

        assertThat(option.getName()).isEqualTo("Test Option");
        assertThat(option.getQuantity()).isEqualTo(100);
        assertThat(option.getProduct()).isEqualTo(product);
    }

    @Test
    void optionSetters() {
        Option option = new Option();
        Product product = new Product();
        option.setName("New Option");
        option.setQuantity(200);
        option.setProduct(product);

        assertThat(option.getName()).isEqualTo("New Option");
        assertThat(option.getQuantity()).isEqualTo(200);
        assertThat(option.getProduct()).isEqualTo(product);
    }
}