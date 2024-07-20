package gift.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import gift.domain.option.entity.Option;
import gift.domain.option.exception.OptionNameDuplicateException;
import gift.domain.option.exception.OptionNameLengthException;
import gift.domain.option.exception.OptionQuantityRangeException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OptionTest {

    @Test
    @DisplayName("이름이 중복일 경우 테스트")
    void checkDuplicateNameTest() {
        List<Option> optionList = Arrays.asList(createOption(), createOption("test2"));
        Option newOption = createOption("test1");

        assertThatThrownBy(() -> newOption.checkDuplicateName(optionList)).isInstanceOf(
            OptionNameDuplicateException.class);
    }

    @Test
    @DisplayName("이름이 중복이 아닐 경우 테스트")
    void checkNotDuplicateNameTest() {
        List<Option> optionList = Arrays.asList(createOption(), createOption("test2"));
        Option newOption = createOption("test3");

        assertDoesNotThrow(() -> newOption.checkDuplicateName(optionList));
    }

    @Test
    @DisplayName("옵션 이름 길이 초과 검사")
    void optionNameLengthFailTest(){
        assertThatThrownBy(() -> {
            new Option("name".repeat(50), 100);
        }).isInstanceOf(OptionNameLengthException.class)
            .hasMessage("옵션 이름 50자 초과");
    }

    @Test
    @DisplayName("정상 옵션 이름 길이 검사")
    void optionNameLengthSuccessTest(){
        assertDoesNotThrow(()->{
            new Option("name", 100);
        });
    }

    @Test
    @DisplayName("수량 1억개 이상일 경우 테스트")
    void optionQuantityExceedTest(){
        assertThatThrownBy(() -> {
            new Option("name", 100_000_000);
        }).isInstanceOf(OptionQuantityRangeException.class)
            .hasMessage("수량은 1개 이상 1억개 미만으로 설정해주세요.");
    }

    @Test
    @DisplayName("수량 1개 미만일 경우 테스트")
    void optionQuantityLackTest(){
        assertThatThrownBy(() -> {
            new Option("name", 0);
        }).isInstanceOf(OptionQuantityRangeException.class)
            .hasMessage("수량은 1개 이상 1억개 미만으로 설정해주세요.");
    }
    @Test
    @DisplayName("수량 정상 테스트")
    void optionQuantitySuccessTest(){
        assertDoesNotThrow(()->{
            new Option("name", 100);
        });
    }

    Option createOption() {
        return createOption("test1");
    }

    Option createOption(String name) {
        return new Option(name, 1000);
    }
}
