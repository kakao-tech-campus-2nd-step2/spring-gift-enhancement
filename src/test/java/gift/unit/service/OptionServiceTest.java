package gift.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import gift.exception.CustomException;
import gift.product.category.entity.Category;
import gift.product.entity.Product;
import gift.product.option.dto.request.CreateOptionRequest;
import gift.product.option.entity.Option;
import gift.product.option.repository.OptionRepository;
import gift.product.option.service.OptionService;
import gift.product.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OptionServiceTest {

    @InjectMocks
    private OptionService optionService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OptionRepository optionRepository;

    @Test
    @DisplayName("옵션 이름 공백 포함 최대 50자 예외")
    void createNameLengthOver50() {
        // given
        Product product = createProduct();
        CreateOptionRequest newOption = new CreateOptionRequest(1L, "a".repeat(51), 1);
        given(productRepository.findById(any())).willReturn(Optional.of(product));

        // when & then
        assertThatThrownBy(() -> optionService.createOption(newOption)).isInstanceOf(
            CustomException.class);
    }

    @Test
    @DisplayName("옵션 수량 1개 이상 1억 개 미만 예외 - 1개 미만")
    void createOptionQuantityUnder1() {
        // given
        Product product = createProduct();
        CreateOptionRequest newOption = new CreateOptionRequest(1L, "a", 0);
        given(productRepository.findById(any())).willReturn(Optional.of(product));

        // when & then
        assertThatThrownBy(() -> optionService.createOption(newOption)).isInstanceOf(
            CustomException.class);
    }

    @Test
    @DisplayName("옵션 수량 1개 이상 1억 개 미만 예외 - 1억 개 이상")
    void createOptionQuantityOver1Million() {
        // given
        Product product = createProduct();
        CreateOptionRequest newOption = new CreateOptionRequest(1L, "a", 100_000_000);
        given(productRepository.findById(any())).willReturn(Optional.of(product));

        // when & then
        assertThatThrownBy(() -> optionService.createOption(newOption)).isInstanceOf(
            CustomException.class);
    }

    @Test
    @DisplayName("동일한 상품 내 옵션 이름 중복 허용 X")
    void createOptionDuplicateOptionNameInProduct() {
        // given
        Product product = createProduct();
        Option option = new Option("a", 2, product);
        CreateOptionRequest newOption = new CreateOptionRequest(1L, "a", 1);
        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(optionRepository.findAllByProduct(any())).willReturn(List.of(option));

        // when & then
        assertThatThrownBy(() -> optionService.createOption(newOption)).isInstanceOf(
            CustomException.class);
    }

    @Test
    @DisplayName("특수 문자 예외 케이스")
    void createOptionSpecialCharacterExceptionTest() {
        // given
        Product product = createProduct();
        CreateOptionRequest newOption = new CreateOptionRequest(1L, "!@#$", 1);
        given(productRepository.findById(any())).willReturn(Optional.of(product));

        // when & then
        assertThatThrownBy(() -> optionService.createOption(newOption)).isInstanceOf(
            CustomException.class);
        then(productRepository).should().findById(any());
    }

    @Test
    @DisplayName("createOption test")
    void createOptionTest() {
        // given
        Product product = createProduct();
        CreateOptionRequest newOption = new CreateOptionRequest(1L, "a", 1);
        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(optionRepository.findAllByProduct(any())).willReturn(List.of());

        // when
        optionService.createOption(newOption);

        // then
        then(productRepository).should().findById(any());
        then(optionRepository).should().save(any());
    }

    @Test
    @DisplayName("getProductOptionTest")
    void getProductOptionTest() {
        // given
        Option newOption = new Option(1L);
        Product product = createProductWithOptions(newOption);
        given(productRepository.findById(any())).willReturn(Optional.of(product));

        // when
        var actual = optionService.getProductOptions(1L);

        // then
        assertThat(actual).hasSize(1);
        then(productRepository).should().findById(any());
    }

    private Product createProduct() {
        return createProduct("Product 1");
    }

    private Product createProduct(String name) {
        return Product.builder()
            .id(1L)
            .name(name)
            .imageUrl("image")
            .category(new Category("category"))
            .price(1000)
            .build();
    }

    private Product createProductWithOptions(Option option) {
        return Product.builder()
            .id(1L)
            .name("Product 1")
            .imageUrl("image")
            .category(new Category("category"))
            .price(1000)
            .options(Set.of(option))
            .build();
    }

}
