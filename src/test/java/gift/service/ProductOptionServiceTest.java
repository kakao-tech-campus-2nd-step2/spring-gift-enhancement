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
class ProductOptionServiceTest {

    private final Pageable pageable = PageRequest.of(0, 10);
    @Autowired
    private ProductOptionService productOptionService;

    @Test
    @DisplayName("정상 옵션 추가하기")
    void successAddOption() {
        //given
        var productOptionRequest = new OptionRequest("옵션1", 1000);
        //when
        var savedProductOption = productOptionService.addOption(1L, productOptionRequest);
        //then
        var productOptionResponseList = productOptionService.getOptions(1L, pageable);
        Assertions.assertThat(productOptionResponseList.size()).isEqualTo(1);

        productOptionService.deleteOption(1L, savedProductOption.id());
    }

    @Test
    @DisplayName("둘 이상의 옵션 추가하기")
    void successAddOptions() {
        //given
        var productOption1Request = new OptionRequest("옵션1", 1000);
        var productOption2Request = new OptionRequest("옵션2", 1000);
        //when
        var savedProductOption1 = productOptionService.addOption(1L, productOption1Request);
        var savedProductOption2 = productOptionService.addOption(1L, productOption2Request);
        //then
        var productOptionResponseList = productOptionService.getOptions(1L, pageable);
        Assertions.assertThat(productOptionResponseList.size()).isEqualTo(2);

        productOptionService.deleteOption(1L, savedProductOption1.id());
        productOptionService.deleteOption(1L, savedProductOption2.id());
    }

    @Test
    @DisplayName("중복된 이름으로 된 상품 옵션 추가시 예외가 발생한다.")
    void failAddOptionWithDuplicatedName() {
        //given
        var productOptionRequest = new OptionRequest("옵션1", 1000);
        var savedProductOption = productOptionService.addOption(1L, productOptionRequest);
        //when, then
        Assertions.assertThatThrownBy(() -> productOptionService.addOption(1L, productOptionRequest)).isInstanceOf(DuplicatedNameException.class);

        productOptionService.deleteOption(1L, savedProductOption.id());
    }

    @Test
    @DisplayName("옵션 수정하기")
    void successUpdateOption() {
        //given
        var productOptionRequest = new OptionRequest("옵션1", 1000);
        var savedOption = productOptionService.addOption(1L, productOptionRequest);
        var optionUpdateDto = new OptionRequest("수정된 옵션", 12345);
        //when
        productOptionService.updateOption(1L, savedOption.id(), optionUpdateDto);
        //then
        var productOptionResponseList = productOptionService.getOptions(1L, pageable);
        var filteredProductOptionResponseList = productOptionResponseList.stream().filter(productOptionResponse -> productOptionResponse.id().equals(savedOption.id())).toList();
        Assertions.assertThat(filteredProductOptionResponseList.size()).isEqualTo(1);
        Assertions.assertThat(filteredProductOptionResponseList.get(0).name()).isEqualTo("수정된 옵션");
        Assertions.assertThat(filteredProductOptionResponseList.get(0).quantity()).isEqualTo(12345);

        productOptionService.deleteOption(1L, savedOption.id());
    }
}
