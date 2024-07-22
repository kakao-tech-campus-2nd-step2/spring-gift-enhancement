package gift.service;

import gift.dto.OptionRequestDto;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import gift.vo.Option;
import gift.vo.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OptionServiceTest {

    @Mock
    private OptionRepository optionRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OptionService optionService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(1L, null, "테스트용 상품", 10000, "http://image.png");
    }

    @Test
    @DisplayName("Test for option name is duplicate")
    void optionNameIsDuplicate() {
        // given
        String duplicateName = "[테스트]옵션입니다";
        OptionRequestDto requestAddOption = new OptionRequestDto(null, product.getId(), duplicateName, 10);
        Option existingOption = new Option(1L, product, duplicateName, 15);  // 이미 존재하는 옵션
        List<OptionRequestDto> requestAddOptions = Collections.singletonList(requestAddOption);

        when(productRepository.findById(requestAddOption.productId())).thenReturn(Optional.of(product));
        when(optionRepository.findByNameAndProductId(duplicateName, product.getId()))
                .thenReturn(Optional.of(existingOption));

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            optionService.addOption(requestAddOptions);
        });

        assertEquals("상품 옵션명이 중복입니다. 다른 옵션명으로 변경해주세요.", exception.getMessage());
    }

    @Test
    @DisplayName("Test for option name is not duplicate")
    void optionNameIsNotDuplicate() {
        // given
        String optionName = "[테스트]추가하려는 옵션입니다";
        OptionRequestDto requestAddOption = new OptionRequestDto(null, product.getId(), optionName, 10);
        List<OptionRequestDto> requestAddOptions = Collections.singletonList(requestAddOption);

        when(productRepository.findById(requestAddOption.productId())).thenReturn(Optional.of(product));
        when(optionRepository.findByNameAndProductId(optionName, product.getId()))
                .thenReturn(Optional.empty());

        // when
        optionService.addOption(requestAddOptions);

        // then
        ArgumentCaptor<Option> optionCaptor = ArgumentCaptor.forClass(Option.class);
        verify(optionRepository).save(optionCaptor.capture());

        Option savedNewOption = optionCaptor.getValue();
        assertAll(
                () -> assertEquals(optionName, savedNewOption.getName()),
                () -> assertEquals(10, savedNewOption.getQuantity()),
                () -> assertEquals(product, savedNewOption.getProduct())
        );
    }
}