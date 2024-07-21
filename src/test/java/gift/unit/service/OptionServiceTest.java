package gift.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

import gift.exception.CustomException;
import gift.product.category.entity.Category;
import gift.product.entity.Product;
import gift.product.option.dto.request.CreateOptionRequest;
import gift.product.option.dto.request.UpdateOptionRequest;
import gift.product.option.entity.Option;
import gift.product.option.entity.Options;
import gift.product.option.repository.OptionJpaRepository;
import gift.product.option.service.OptionService;
import gift.product.repository.ProductJpaRepository;
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
    private ProductJpaRepository productRepository;

    @Mock
    private OptionJpaRepository optionRepository;

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
        given(optionRepository.findAllByProduct(any())).willReturn(new Options(List.of(option)));

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
        given(optionRepository.findAllByProduct(any())).willReturn(new Options(List.of()));
        given(optionRepository.save(any())).willReturn(new Option(1L));

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

    @Test
    @DisplayName("옵션 수량 수정 1개 이상 1억 개 미만 - 1개 미만")
    void updateOptionQuantityUnder1() {
        // given
        Option option = new Option(1L, 100);
        Product product = createProductWithOptions(option);
        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(optionRepository.findAllByProduct(any())).willReturn(new Options(List.of()));
        given(optionRepository.findById(any())).willReturn(Optional.of(option));

        UpdateOptionRequest request = new UpdateOptionRequest("a", 0);

        // when & then
        assertThatThrownBy(() -> optionService.updateOption(1L, 1L, request))
            .isInstanceOf(CustomException.class);
        then(optionRepository).should().findById(any());
    }

    @Test
    @DisplayName("옵션 수량 수정 1개 이상 1억 개 미만 - 1억 개 이상")
    void updateOptionQuantityOver1Million() {
        // given
        Option option = new Option(1L, 1000);
        Product product = createProductWithOptions(option);
        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(optionRepository.findAllByProduct(any())).willReturn(new Options(List.of()));
        given(optionRepository.findById(any())).willReturn(Optional.of(option));

        UpdateOptionRequest request = new UpdateOptionRequest("a", 100_000_000);

        // when & then
        assertThatThrownBy(() -> optionService.updateOption(1L, 1L, request))
            .isInstanceOf(CustomException.class);
        then(optionRepository).should().findById(any());
    }

    @Test
    @DisplayName("수정된 옵션 이름 중복")
    void updateOptionIfNameDuplicate() {
        // given
        Product product = createProduct();
        Option option1 = new Option(1L, "a", 100, product);
        Option option2 = new Option(2L, "b", 100, product);
        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(optionRepository.findById(any())).willReturn(Optional.of(option1));
        given(optionRepository.findAllByProduct(any())).willReturn(new Options(List.of(option1, option2)));

        UpdateOptionRequest newOption = new UpdateOptionRequest("b", 1000);

        // when & then
        assertThatThrownBy(
            () -> optionService.updateOption(1L, 1L, newOption))
            .isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("옵션 수정 특수문자 예외")
    void updateOptionSpecialCharacterException() {
        // given
        Option option = new Option(1L, 100);
        Product product = createProductWithOptions(option);
        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(optionRepository.findAllByProduct(any())).willReturn(new Options(List.of()));
        given(optionRepository.findById(any())).willReturn(Optional.of(option));

        UpdateOptionRequest request = new UpdateOptionRequest("!@#$", 100);

        // when & then
        assertThatThrownBy(() -> optionService.updateOption(1L, 1L, request))
            .isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("update Option test")
    void updateOptionTest() {
        // given
        Option option = new Option(1L, 100);
        Product product = createProductWithOptions(option);
        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(optionRepository.findAllByProduct(any())).willReturn(new Options(List.of()));
        given(optionRepository.findById(any())).willReturn(Optional.of(option));

        UpdateOptionRequest request = new UpdateOptionRequest("updated", 1000);

        // when
        optionService.updateOption(1L, 1L, request);

        // then
        assertThat(option.getName()).isEqualTo("updated");
        assertThat(option.getQuantity()).isEqualTo(1000);
    }

    @Test
    @DisplayName("유일한 옵션 삭제 불가")
    void deleteLastOption() {
        // given
        Option option = new Option(1L, 100);
        Product product = createProductWithOptions(option);
        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(optionRepository.findById(any())).willReturn(Optional.of(option));

        // when & then
        assertThatThrownBy(() -> optionService.deleteOption(1L, 1L)).isInstanceOf(
            CustomException.class);
    }

    @Test
    @DisplayName("해당 상품의 옵션이 아닌 경우")
    void deleteOtherProductOption() {
        // given
        Option option = new Option(1L, 100);
        Option productOption = new Option(2L, 100);
        Product product = createProductWithOptions(productOption);
        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(optionRepository.findById(any())).willReturn(Optional.of(option));

        // when & then
        assertThatThrownBy(() -> optionService.deleteOption(1L, 1L)).isInstanceOf(
            CustomException.class);
    }

    @Test
    @DisplayName("deleteOption test")
    void deleteOptionTest() {
        // given
        Option option1 = new Option(1L, 100);
        Option option2 = new Option(2L, 100);
        Product product = createProductWithOptions(option1, option2);
        given(productRepository.findById(any())).willReturn(Optional.of(product));
        given(optionRepository.findById(any())).willReturn(Optional.of(option1));
        willDoNothing().given(optionRepository).delete(any());

        // when
        optionService.deleteOption(1L, 1L);

        // then
        then(optionRepository).should().delete(any());
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
            .options()
            .build();
    }

    private Product createProductWithOptions(Option... option) {
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
