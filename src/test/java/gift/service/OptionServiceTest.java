package gift.service;

import gift.dto.request.OptionRequest;
import gift.entity.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("옵션 서비스 단위테스트")
class OptionServiceTest {

    @InjectMocks
    private OptionService optionService;

    @Test
    @DisplayName("특정 상품 옵션 조회")
    void getOptions() {
        //Given
        List<OptionRequest> optionRequests = List.of(new OptionRequest("옵션1", 3030));

        //When
        List<Option> options = optionService.convertToOptions(optionRequests);

        //Then
        assertThat(options).hasSize(1)
                .extracting("name")
                .containsExactly("옵션1");
    }
}
