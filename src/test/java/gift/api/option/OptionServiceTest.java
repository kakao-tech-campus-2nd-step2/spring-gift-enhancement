package gift.api.option;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.then;

import gift.api.option.domain.Option;
import gift.api.option.dto.OptionRequest;
import gift.api.option.dto.OptionResponse;
import gift.api.product.Product;
import gift.api.product.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OptionServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private OptionRepository optionRepository;
    @InjectMocks
    private OptionService optionService;

    @Test
    @DisplayName(value = "옵션_조회_테스트")
    void getOptions() {
        // given
        var productId = 1L;
        var option1 = mock(Option.class);
        var option2 = mock(Option.class);
        given(optionRepository.findAllByProductId(any()))
            .willReturn(List.of(option1, option2));

        // when
        // then
        assertThat(optionService.getOptions(productId))
            .isEqualTo(List.of(OptionResponse.of(option1), OptionResponse.of(option2)));
    }

    @Test
    @DisplayName(value = "정상_옵션_추가_테스트")
    void add() {
        // given
        var product = mock(Product.class);
        given(productRepository.findById(any()))
            .willReturn(Optional.of(product));
        given(optionRepository.findAllByProductId(any()))
            .willReturn(List.of(new Option(product, "test", 820)));

        // when
        optionService.add(1L, new OptionRequest("name", 421));

        // then
        then(optionRepository).should().save(any());
    }

    @Test
    @DisplayName(value = "중복_이름_옵션_추가_테스트")
    void addRedundantOption() {
        // given
        var product = mock(Product.class);
        var optionRequest = new OptionRequest("name", 123);
        given(productRepository.findById(any()))
            .willReturn(Optional.of(product));
        given(optionRepository.findAllByProductId(any()))
            .willReturn(List.of(optionRequest.toEntity(product)));

        // when
        // then
        assertThatExceptionOfType(InvalidNameException.class)
            .isThrownBy(() -> optionService.add(1L, optionRequest));
    }
}