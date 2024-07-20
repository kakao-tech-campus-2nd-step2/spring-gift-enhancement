package gift.option.domain;

import gift.product.domain.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OptionTest {
    @Test
    void testOptionEqualsAndHashCode_ById() {
        OptionName optionName1 = new OptionName("Name1");
        OptionName optionName2 = new OptionName("Name2");
        OptionCount optionCount1 = new OptionCount(10L);
        OptionCount optionCount2 = new OptionCount(20L);
        Product product = new Product(); // assuming a default constructor for simplicity

        Option option1 = new Option(1L, optionName1, optionCount1, product);
        Option option2 = new Option(1L, optionName2, optionCount2, product);
        Option option3 = new Option(2L, optionName1, optionCount1, product);

        assertThat(option1).isEqualTo(option2);
        assertThat(option1).isNotEqualTo(option3);
        assertThat(option1.hashCode()).isEqualTo(option2.hashCode());
        assertThat(option1.hashCode()).isNotEqualTo(option3.hashCode());
    }
}