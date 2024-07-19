package gift.application;

import gift.global.error.CustomException;
import gift.global.error.ErrorCode;
import gift.product.application.OptionService;
import gift.product.dao.OptionRepository;
import gift.product.dao.ProductRepository;
import gift.product.dto.OptionRequest;
import gift.product.dto.OptionResponse;
import gift.product.entity.Category;
import gift.product.entity.Option;
import gift.product.entity.Product;
import gift.product.util.OptionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OptionServiceTest {

    @InjectMocks
    private OptionService optionService;

    @Mock
    private OptionRepository optionRepository;

    @Mock
    private ProductRepository productRepository;

    private final Category category = new Category.CategoryBuilder()
            .setName("상품권")
            .setColor("#ffffff")
            .setImageUrl("https://product-shop.com")
            .setDescription("")
            .build();

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product.ProductBuilder()
                .setName("product")
                .setPrice(1000)
                .setCategory(category)
                .setImageUrl("https://product-shop.com")
                .build();
    }

    @Test
    @DisplayName("상품 옵션 조회 기능 테스트")
    void getProductOptionsByIdOrThrow() {
        Long productId = 1L;
        Option option1 = new Option("옵션1", 10, product);
        Option option2 = new Option("옵션2", 20, product);
        product.addOption(option1);
        product.addOption(option2);
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));

        Set<OptionResponse> responses = optionService.getProductOptionsByIdOrThrow(productId);

        assertThat(responses).contains(
                OptionMapper.toResponseDto(option1),
                OptionMapper.toResponseDto(option2)
        );
    }

    @Test
    @DisplayName("상품 옵션 추가 기능 테스트")
    void addOptionToProduct() {
        Long productId = 1L;
        OptionRequest request = new OptionRequest("옵션", 10);
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));

        OptionResponse response = optionService.addOptionToProduct(productId, request);

        assertThat(response.name()).isEqualTo(request.name());
        assertThat(response.quantity()).isEqualTo(request.quantity());
    }

    @Test
    @DisplayName("상품 옵션 추가 실패 테스트")
    void addOptionToProductFailed() {
        Long productId = 1L;
        OptionRequest request = new OptionRequest("옵션", 10);
        product.addOption(OptionMapper.toEntity(request, product));
        given(productRepository.findById(anyLong()))
                .willReturn(Optional.of(product));

        assertThatThrownBy(() -> optionService.addOptionToProduct(productId, request))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.OPTION_ALREADY_EXISTS
                                     .getMessage());
    }

    @Test
    @DisplayName("상품 옵션 삭제 기능 테스트")
    void deleteOptionFromProduct() {
        Long productId = 1L;
        OptionRequest request = new OptionRequest("옵션1", 10);
        Option option1 = OptionMapper.toEntity(request, product);
        Option option2 = new Option("옵션2", 100, product);
        product.addOption(option1);
        product.addOption(option2);
        given(productRepository.findById(anyLong()))
                .willReturn(Optional.of(product));
        given(optionRepository.findByProduct_IdAndName(any(), anyString()))
                .willReturn(Optional.of(option1));

        optionService.deleteOptionFromProduct(productId, request);

        verify(productRepository).findById(anyLong());
        verify(optionRepository).findByProduct_IdAndName(any(), anyString());
    }

    @Test
    @DisplayName("상품 옵션 삭제 실패 테스트")
    void deleteOptionFromProductFailed() {
        Long productId = 1L;
        OptionRequest request = new OptionRequest("옵션1", 10);
        product.addOption(OptionMapper.toEntity(request, product));
        given(productRepository.findById(anyLong()))
                .willReturn(Optional.of(product));

        assertThatThrownBy(() -> optionService.deleteOptionFromProduct(productId, request))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.OPTION_REMOVE_FAILED
                                     .getMessage());
    }

    @Test
    @DisplayName("상품 옵션 수량 차감 기능 테스트")
    void subtractQuantityOfOption() {
        Long productId = 1L;
        int quantity = 5;
        OptionRequest request = new OptionRequest("옵션1", 10);
        Option option = OptionMapper.toEntity(request, product);
        int result = option.getQuantity() - quantity;
        given(optionRepository.findByProduct_IdAndName(anyLong(), anyString()))
                .willReturn(Optional.of(option));

        optionService.subtractQuantityOfOption(productId, request, quantity);

        verify(optionRepository).findByProduct_IdAndName(anyLong(), anyString());
        assertThat(option.getQuantity()).isEqualTo(result);
    }

    @Test
    @DisplayName("상품 옵션 수량 차감 실패 테스트")
    void subtractQuantityOfOptionFailed() {
        Long productId = 1L;
        OptionRequest request = new OptionRequest("옵션1", 10);
        Option option = OptionMapper.toEntity(request, product);
        int quantity = option.getQuantity() + 1;
        given(optionRepository.findByProduct_IdAndName(anyLong(), anyString()))
                .willReturn(Optional.of(option));

        assertThatThrownBy(() -> optionService.subtractQuantityOfOption(productId, request, quantity))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.OPTION_QUANTITY_SUBTRACT_FAILED
                                     .getMessage());
    }

}