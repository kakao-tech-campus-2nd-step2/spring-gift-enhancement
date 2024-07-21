package gift.service;

import gift.domain.Category;
import gift.domain.Option;
import gift.domain.Product;
import gift.dto.request.OptionRequestDto;
import gift.dto.response.OptionResponseDto;
import gift.exception.customException.DenyDeleteException;
import gift.exception.customException.EntityNotFoundException;
import gift.exception.customException.NameDuplicationException;
import gift.exception.customException.OptionQuantityNotMinusException;
import gift.repository.option.OptionRepository;
import gift.repository.product.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static gift.exception.exceptionMessage.ExceptionMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class OptionServiceTest {

    @InjectMocks
    OptionService optionService;

    @Mock
    OptionRepository optionRepository;

    @Mock
    ProductRepository productRepository;

    @Test
    @DisplayName("옵션 저장 테스트")
    void 옵션_저장_테스트(){
        //given
        Long validProductId = 1L;
        Long inValidProductId = 2L;

        Category category = new Category("상품권", "#0000");

        Product product = new Product.Builder()
                .name("테스트 상품")
                .price(1000)
                .imageUrl("abc.png")
                .category(category)
                .build();

        OptionRequestDto validOptionDto = new OptionRequestDto("TEST", 1);
        OptionRequestDto inValidOptionDto = new OptionRequestDto("invalid", 1);
        Option option = new Option(validOptionDto.optionName(), validOptionDto.optionQuantity());
        option.addProduct(product);
        Option inValidOption = new Option(validOptionDto.optionName(), validOptionDto.optionQuantity());

        given(productRepository.findById(validProductId)).willReturn(Optional.of(product));
        given(productRepository.findById(inValidProductId)).willReturn(Optional.empty());

        given(optionRepository.findOptionByNameAndProductId(validOptionDto.optionName(), validProductId)).willReturn(Optional.empty());
        given(optionRepository.findOptionByNameAndProductId(inValidOptionDto.optionName(), validProductId)).willReturn(Optional.of(inValidOption));
        given(optionRepository.save(any(Option.class))).willReturn(option);

        //when
        OptionResponseDto optionResponseDto = optionService.saveOption(validOptionDto, validProductId);

        //then
        assertAll(
                () -> assertThat(optionResponseDto.name()).isEqualTo(option.getName()),
                () -> assertThat(optionResponseDto.quantity()).isEqualTo(option.getQuantity()),
                () -> assertThatThrownBy(() -> optionService.saveOption(validOptionDto, inValidProductId))
                        .isInstanceOf(EntityNotFoundException.class)
                        .hasMessage(PRODUCT_NOT_FOUND),
                () -> assertThatThrownBy(() -> optionService.saveOption(inValidOptionDto, validProductId))
                        .isInstanceOf(NameDuplicationException.class)
                        .hasMessage(OPTION_NAME_DUPLICATION)
        );
    }

    @Test
    @DisplayName("상품으로 옵션 조회 테스트")
    void 상품_옵션_조회_테스트(){
        //given
        Option option1 = new Option("TEST1", 1);
        Option option2 = new Option("TEST2", 2);
        Option option3 = new Option("TEST3", 3);

        List<Option> options = Arrays.asList(option1, option2, option3);

        given(optionRepository.findOptionsByProductId(anyLong())).willReturn(options);

        //when
        List<OptionResponseDto> optionDtos = optionService.findOptionsByProduct(1L);

        //then
        assertThat(optionDtos.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("옵션 삭제 테스트")
    void 옵션_삭제_테스트(){
        //given
        Long validOptionId = 1L;
        Long inValidOptionId = 2L;

        Long validProductId = 1L;
        Long inValidProductId = 2L;

        Option option = new Option("TEST", 1);

        given(optionRepository.findById(validOptionId)).willReturn(Optional.of(option));
        given(optionRepository.findById(inValidOptionId)).willReturn(Optional.empty());
        given(optionRepository.countOptionByProductId(validProductId)).willReturn(2L);
        given(optionRepository.countOptionByProductId(inValidProductId)).willReturn(1L);

        //when
        OptionResponseDto optionResponseDto = optionService.deleteOneOption(validProductId, validOptionId);

        //then
        assertAll(
                () -> assertThat(optionResponseDto.name()).isEqualTo(option.getName()),
                () -> assertThat(optionResponseDto.quantity()).isEqualTo(option.getQuantity()),
                () -> assertThatThrownBy(() -> optionService.deleteOneOption(validProductId, inValidOptionId))
                        .isInstanceOf(EntityNotFoundException.class)
                        .hasMessage(OPTION_NOT_FOUND),
        () -> assertThatThrownBy(() -> optionService.deleteOneOption(inValidProductId, validOptionId))
                .isInstanceOf(DenyDeleteException.class)
                .hasMessage(DENY_OPTION_DELETE)
        );
    }

    @Test
    @DisplayName("옵션 수량 초과 감소 시 Exception 테스트")
    void 옵션_수량_초과_감소_EXCEPTION_TEST(){
        //given
        Long optionId = 1L;
        Option option = new Option("TEST_OPTION", 100);

        given(optionRepository.findOptionByIdForUpdate(optionId)).willReturn(Optional.of(option));

        //expected
        assertThatThrownBy(() -> optionService.updateOptionQuantity(optionId, 101))
                        .isInstanceOf(OptionQuantityNotMinusException.class)
                        .hasMessage(OPTION_QUANTITY_NOT_MINUS);

    }

    @Test
    @DisplayName("옵션 수량 감소 정상")
    void 옵션_수량__감소_정상_TEST(){
        //given
        Long optionId = 1L;
        Option option = new Option("TEST_OPTION", 100);

        given(optionRepository.findOptionByIdForUpdate(optionId)).willReturn(Optional.of(option));

        //when
        OptionResponseDto optionResponseDto = optionService.updateOptionQuantity(optionId, 50);

        //then
        assertAll(
                () -> assertThat(optionResponseDto.name()).isEqualTo(option.getName()),
                () -> assertThat(optionResponseDto.quantity()).isEqualTo(50)
        );

    }




}