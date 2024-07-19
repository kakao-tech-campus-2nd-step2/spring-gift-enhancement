package gift.service;

import static gift.util.CategoryFixture.createCategory;
import static gift.util.OptionFixture.createOption;
import static gift.util.ProductFixture.createProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import gift.domain.Category;
import gift.domain.Option;
import gift.domain.Product;
import gift.dto.OptionDTO;
import gift.exception.DuplicateOptionNameException;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OptionServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private OptionRepository optionRepository;

    @InjectMocks
    private OptionService optionService;

    private Category category;
    private Product product;

    @BeforeEach
    void setup() {
        category = createCategory(1L, "test");
        product = createProduct(1L, "아이스 아메리카노", category);
    }

    @DisplayName("한 상품의 옵션 조회")
    @Test
    void getOptions() {
        // given
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(optionRepository.findByProduct(any(Product.class)))
            .willReturn(List.of(createOption(1L, "test1", product), createOption(2L, "test2", product)));

        // when
        List<OptionDTO> actual = optionService.getOptions(product.getId());

        // then
        assertThat(actual).hasSize(2);
    }

    @DisplayName("옵션 추가")
    @Test
    void addOption() {
        // given
        Option option = createOption(1L, "test", product);
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(optionRepository.save(any(Option.class))).willReturn(option);

        // when
        OptionDTO actual = optionService.addOption(product.getId(), option.toDTO());

        // then
        assertThat(actual).isEqualTo(option.toDTO());
    }

    @DisplayName("중복된 이름의 옵션 추가")
    @Test
    void addDuplicateOption() {
        // given
        Option option = createOption(1L, "test", product);
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(optionRepository.findByProduct(any(Product.class))).willReturn(List.of(option));

        // when
        // then
        assertThatExceptionOfType(DuplicateOptionNameException.class)
            .isThrownBy(() -> optionService.addOption(product.getId(), option.toDTO()));
    }

    @DisplayName("옵션 수정")
    @Test
    void updateOption() {
        // given
        long id = 1L;
        Option option = createOption(id, "test", product);
        Option updatedOption = createOption(id, "updatedTest", product);
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(optionRepository.findByProductAndId(any(Product.class), anyLong())).willReturn(Optional.of(option));
        given(optionRepository.save(any(Option.class))).willReturn(updatedOption);

        // when
        OptionDTO actual = optionService.updateOption(product.getId(), updatedOption.toDTO());

        // then
        assertThat(actual).isEqualTo(updatedOption.toDTO());
    }
}
