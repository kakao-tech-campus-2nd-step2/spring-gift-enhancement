package gift.model;

import gift.common.exception.DuplicateDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ProductTest {

    @Test
    @DisplayName("옵션명 중복 체크[실패] - 중복된 이름")
    void checkDuplicateName() {
        // given
        Category category = new Category();
        List<Option> options = List.of(new Option("oName", 100), new Option("oName", 1100));

        // when
        // then
        assertThatExceptionOfType(DuplicateDataException.class)
                .isThrownBy(() -> new Product("pname", 1000, "purl", category, options));
    }

    @Test
    @DisplayName("Product의 options 필드에 모든 옵션 add 테스트[성공]")
    void createProduct() {
        // given
        Category category = new Category();
        List<Option> options = List.of(new Option("oName1", 100), new Option("oName2", 1100));

        // when
        Product product = new Product("pname", 1000, "purl", category, options);

        // then
        assertThat(product.getOptions()).hasSize(options.size());
    }

    @Test
    @DisplayName("Option에 Product가 들어가는지 테스트[성공]")
    void checkOptionInserted() {
        // given
        String oName1 = "oName1";
        String oName2 = "oName2";
        Category category = new Category();
        List<Option> options = List.of(new Option("oName1", 100), new Option("oName2", 100));
        String pname = "pname";
        int price = 1_000;
        String purl = "purl";
        // when
        Product product = new Product(pname, price, purl, category, options);

        // then
        assertThat(product.getOptions()).hasSize(options.size());
        assertThat(product.getOptions().get(0).getProduct().getName()).isEqualTo(pname);
        assertThat(product.getOptions().get(0).getProduct().getPrice()).isEqualTo(price);
        assertThat(product.getOptions().get(0).getProduct().getImageUrl()).isEqualTo(purl);

        assertThat(product.getOptions().get(0).getName()).isEqualTo(oName1);
        assertThat(product.getOptions().get(1).getName()).isEqualTo(oName2);
    }
}