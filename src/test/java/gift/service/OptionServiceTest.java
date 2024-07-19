package gift.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import gift.exception.customException.CustomNotFoundException;
import gift.model.item.Item;
import gift.model.option.Option;
import gift.model.option.OptionDTO;
import gift.repository.ItemRepository;
import gift.repository.OptionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class OptionServiceTest {

    @Autowired
    private OptionService optionService;

    @MockBean
    private OptionRepository optionRepository;

    @MockBean
    private ItemRepository itemRepository;

    private final String testName1 = "option1";
    private final String testName2 = "option2";
    private final String testName3 = "option3";
    private final Long testQuantity = 100L;
    private final Item testItem = new Item("item", 1000L, "img", null);

    @DisplayName("옵션 추가 성공 테스트")
    @Test
    void testInsertOptionSuccess() {
        OptionDTO optionDTO = new OptionDTO(testName1, testQuantity);
        given(itemRepository.findById(any())).willReturn(Optional.of(testItem));
        given(optionRepository.save(any())).willReturn(
            new Option(testName1, testQuantity, testItem));
        OptionDTO result = optionService.insertOption(0L, optionDTO);
        assertThat(result).usingRecursiveComparison().isEqualTo(optionDTO);
    }

    @DisplayName("옵션 추가 실패 테스트(존재하지 않는 아이템)")
    @Test
    void testInsertOptionNotExistItem() {
        OptionDTO optionDTO = new OptionDTO(testName1, testQuantity);
        given(itemRepository.findById(any())).willReturn(Optional.ofNullable(null));
        assertThatExceptionOfType(CustomNotFoundException.class).isThrownBy(
            () -> optionService.insertOption(0L, optionDTO));
    }

    @DisplayName("옵션 목록 조회 성공 테스트")
    @Test
    void testFindAllOption() {
        Option option1 = new Option(testName1, testQuantity, testItem);
        Option option2 = new Option(testName2, testQuantity, testItem);
        Option option3 = new Option(testName3, testQuantity, testItem);
        List<Option> list = new ArrayList<>(List.of(option1, option2, option3));
        given(optionRepository.findAllByItemId(any())).willReturn(list);
        given(itemRepository.existsById(any())).willReturn(true);
        List<OptionDTO> result = optionService.getOptionList(0L);

        assertThat(result).usingRecursiveComparison()
            .isEqualTo(list.stream().map(Option::toDTO).toList());
    }

    @DisplayName("옵션 목록 조회 실패 테스트")
    @Test
    void testFindAllOptionFail() {
        given(itemRepository.existsById(any())).willReturn(false);

        assertThatExceptionOfType(CustomNotFoundException.class).isThrownBy(
            () -> optionService.getOptionList(0L));
    }

}
