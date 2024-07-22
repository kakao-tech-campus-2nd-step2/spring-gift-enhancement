package gift.service;

import gift.domain.category.Category;
import gift.domain.option.Option;
import gift.domain.option.OptionRepository;
import gift.domain.product.Product;
import gift.domain.product.ProductRepository;
import gift.dto.OptionRequestDto;
import gift.dto.OptionResponseDto;
import gift.exception.CustomException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OptionServiceTest {
    @Mock
    private OptionRepository optionRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private OptionService optionService;

    @Test
    void 옵션_조회_성공() {
        //given
        Product product = new Product("더미", 10000, "test.jpg",
                new Category("테스트", "##", "설명", "test"));
        Option expected1 = new Option("옵션1",1000,product);
        Option expected2 = new Option("옵션2",100,product);

        given(productRepository.findById(any()))
                .willReturn(Optional.of(product));

        //when
        List<OptionResponseDto> actual = optionService.getOptions(1L);

        //then
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getName()).isEqualTo("옵션1");
        assertThat(actual.get(1).getName()).isEqualTo("옵션2");

    }

    @Test
    void 옵션_생성_성공() {
        //given
        given(productRepository.findById(any()))
                .willReturn(Optional.of(new Product("더미", 10000, "test.jpg",
                        new Category("테스트", "##", "설명", "test"))));

        //when
        optionService.saveOption(1L,new OptionRequestDto("새 옵션",10,1L));

        //then
        verify(optionRepository).save(any());
    }

    @Test
    void 옵션_생성_실패_중복된_이름() {
        //given
        given(productRepository.findById(any()))
                .willReturn(Optional.of(new Product("더미", 10000, "test.jpg",
                        new Category("테스트", "##", "설명", "test"))));
        //when
        optionService.saveOption(1L,new OptionRequestDto("중복이름",10,1L));
        var request = new OptionRequestDto("중복이름",10,1L);
        //then
        assertThatExceptionOfType(CustomException.class)
                .isThrownBy(() -> optionService.saveOption(1L, request));
    }
}
