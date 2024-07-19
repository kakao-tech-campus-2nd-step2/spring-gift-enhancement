package gift.service;

import gift.dto.request.OptionRequest;
import gift.entity.Option;
import gift.exception.InsufficientOptionQuantityException;
import gift.exception.OptionDuplicateException;
import gift.exception.OptionNotFoundException;
import gift.repository.OptionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("옵션 서비스 단위테스트")
class OptionServiceTest {

    @InjectMocks
    private OptionService optionService;
    @Mock
    private OptionRepository optionRepository;

    @Test
    @DisplayName("특정 상품 옵션 조회")
    void convertToOptions() {
        //Given
        List<OptionRequest> optionRequests = List.of(new OptionRequest("옵션1", 3030));

        //When
        List<Option> options = optionService.convertToOptions(optionRequests);

        //Then
        assertThat(options).hasSize(1)
                .extracting("name")
                .containsExactly("옵션1");
    }

    @Test
    @DisplayName("옵션 이름 중복 체크 - 중복 적발")
    void checkDuplicateName() {
        //Given
        List<Option> existingOptions = List.of(new Option("이미존재", 100));
        String newName = "이미존재";

        //When Then
        assertThatThrownBy(() -> optionService.checkDuplicateOptionName(existingOptions, newName))
                .isInstanceOf(OptionDuplicateException.class);
    }

    @Test
    @DisplayName("옵션 존재 유무 - 존재 함")
    void checkOptionIdExist() {
        //Given
        Long targetOptionId = 1L;
        Option existingOption1 = mock(Option.class);
        List<Option> options = List.of(
                existingOption1
        );

        when(existingOption1.getId()).thenReturn(1L);

        //When
        Option option = optionService.checkOptionIdExist(targetOptionId, options);

        //Then
        assertThat(option.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("옵션 존재 유무 - 존재 안함")
    void checkOptionIdExist2() {
        //Given
        Long targetOptionId = 2L;
        Option existingOption1 = mock(Option.class);
        List<Option> options = List.of(
                existingOption1
        );

        when(existingOption1.getId()).thenReturn(1L);

        //When Then
        assertThatThrownBy(() -> optionService.checkOptionIdExist(targetOptionId, options))
                .isInstanceOf(OptionNotFoundException.class);
    }

    @Test
    @DisplayName("옵션 수량 차감 - 넉넉한 수량이 있어 차감 성공")
    void subtractOptionQuantity() {
        //Given
        Long targetOptionId = 1L;
        Option targetOption = mock(Option.class);
        int subtractQuantity = 99_999_999;

        when(optionRepository.findByIdWithPessimisticWriteLock(targetOptionId))
                .thenReturn(Optional.of(targetOption));
        when(targetOption.getQuantity()).thenReturn(100_000_000);

        //When
        optionService.subtractOptionQuantity(targetOptionId, subtractQuantity);

        //Then
        verify(targetOption, times(1)).subtract(subtractQuantity);
    }

    @Test
    @DisplayName("옵션 수량 차감 - 차감 원하는 수량보다 기존 수량이 적어 실패")
    void subtractOptionQuantity2() {
        //Given
        Long targetOptionId = 1L;
        Option targetOption = mock(Option.class);
        int subtractQuantity = 999_999_999;

        when(optionRepository.findByIdWithPessimisticWriteLock(targetOptionId))
                .thenReturn(Optional.of(targetOption));
        when(targetOption.getQuantity()).thenReturn(100);

        //When Then
        assertThatThrownBy(() -> optionService.subtractOptionQuantity(targetOptionId, subtractQuantity))
                .isInstanceOf(InsufficientOptionQuantityException.class);
    }
}
