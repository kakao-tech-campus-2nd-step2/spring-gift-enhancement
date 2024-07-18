package gift.entity;

import static org.assertj.core.api.Assertions.assertThat;

import gift.dto.OptionDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OptionTest {

    @Test
    @DisplayName("옵션 정보 수정 테스트")
    void updateOption() {
        Category category = new Category("생일선물", "Red", "http", "생일선물 카테고리");
        Product product = new Product("kakao", 5000L, "https", category);
        Option option = new Option("Large", 100, product);
        OptionDto optionDto = new OptionDto(null, "Medium", 30, null);

        option.updateOption(optionDto);
        assertThat(option.getName()).isEqualTo(optionDto.getName());
        assertThat(option.getQuantity()).isEqualTo(optionDto.getQuantity());
    }
}