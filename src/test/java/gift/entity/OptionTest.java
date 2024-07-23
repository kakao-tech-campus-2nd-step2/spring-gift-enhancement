package gift.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import gift.exception.MinimumOptionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OptionTest {

    private Option option;

    @BeforeEach
    void setUp() {
        option = new Option("옵션", 500, new Product());
    }

    @Test
    @DisplayName("생성자 테스트")
    void OptionConstructorTest() {
        Option newoption = new Option("new 옵션", 1000, new Product());

        assertThat(newoption).isNotNull();
        assertThat(newoption.getName()).isEqualTo("new 옵션");
        assertThat(newoption.getQuantity()).isEqualTo(1000);
    }

    @Test
    @DisplayName("Getter 테스트")
    void ProductGetterTest() {
        assertThat(option.getName()).isEqualTo("옵션");
        assertThat(option.getQuantity()).isEqualTo(500);
    }

    @Test
    @DisplayName("updateOption 테스트")
    void updateOptionTest() {
        option.updateOption("업데이트 옵션", 101010, new Product());

        assertThat(option.getName()).isEqualTo("업데이트 옵션");
        assertThat(option.getQuantity()).isEqualTo(101010);
    }

    @Test
    @DisplayName("subtractQuantity 테스트")
    void subtractQuantityTest() {
        option.subtractQuantity(300);

        assertThat(option.getQuantity()).isEqualTo(200);
    }

    @Test
    @DisplayName("subtractQuantity 결과가 1이하 일때 오류 방출 테스트")
    void subtractQuantityErrorTest() {

        assertThatThrownBy(() -> option.subtractQuantity(499))
            .isInstanceOf(MinimumOptionException.class)
            .hasMessage("옵션의 수량을 1개 이하로 남길 수 없습니다.");
    }

    @Test
    @DisplayName("sameName 테스트")
    void sameNameTest() {
        Option option2 = new Option("옵션", 400, new Product());

        assertThat(option2.sameName(option.getName())).isTrue();
        assertThat(option2.sameName("옵션")).isTrue();
        assertThat(option2.sameName("다른옵션")).isFalse();
    }
}
