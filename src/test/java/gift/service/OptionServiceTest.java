package gift.service;

import gift.dto.OptionRequest;
import gift.exception.DuplicatedNameException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OptionServiceTest {

    private final Pageable pageable = PageRequest.of(0, 10);
    @Autowired
    private OptionService optionService;

    @Test
    @DisplayName("정상 옵션 추가하기")
    void successAddOption() {
        //given
        var optionRequest = new OptionRequest("옵션1", 1000);
        //when
        var savedOption = optionService.addOption(1L, optionRequest);
        //then
        var options = optionService.getOptions(1L, pageable);
        Assertions.assertThat(options.size()).isEqualTo(1);

        optionService.deleteOption(1L, savedOption.id());
    }

    @Test
    @DisplayName("둘 이상의 옵션 추가하기")
    void successAddOptions() {
        //given
        var optionRequest1 = new OptionRequest("옵션1", 1000);
        var optionRequest2 = new OptionRequest("옵션2", 1000);
        //when
        var savedOption1 = optionService.addOption(1L, optionRequest1);
        var savedOption2 = optionService.addOption(1L, optionRequest2);
        //then
        var options = optionService.getOptions(1L, pageable);
        Assertions.assertThat(options.size()).isEqualTo(2);

        optionService.deleteOption(1L, savedOption1.id());
        optionService.deleteOption(1L, savedOption2.id());
    }

    @Test
    @DisplayName("중복된 이름으로 된 상품 옵션 추가시 예외가 발생한다.")
    void failAddOptionWithDuplicatedName() {
        //given
        var optionRequest = new OptionRequest("옵션1", 1000);
        var savedOption = optionService.addOption(1L, optionRequest);
        //when, then
        Assertions.assertThatThrownBy(() -> optionService.addOption(1L, optionRequest)).isInstanceOf(DuplicatedNameException.class);

        optionService.deleteOption(1L, savedOption.id());
    }

    @Test
    @DisplayName("옵션 수정하기")
    void successUpdateOption() {
        //given
        var optionRequest = new OptionRequest("옵션1", 1000);
        var savedOption = optionService.addOption(1L, optionRequest);
        var optionUpdateDto = new OptionRequest("수정된 옵션", 12345);
        //when
        optionService.updateOption(1L, savedOption.id(), optionUpdateDto);
        //then
        var options = optionService.getOptions(1L, pageable);
        var filteredOptions = options.stream().filter(productOptionResponse -> productOptionResponse.id().equals(savedOption.id())).toList();
        Assertions.assertThat(filteredOptions.size()).isEqualTo(1);
        Assertions.assertThat(filteredOptions.get(0).name()).isEqualTo("수정된 옵션");
        Assertions.assertThat(filteredOptions.get(0).quantity()).isEqualTo(12345);

        optionService.deleteOption(1L, savedOption.id());
    }
}
