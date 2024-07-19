package gift.service;

import static gift.util.CategoryFixture.createCategory;
import static gift.util.OptionFixture.createOption;
import static gift.util.ProductFixture.createProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import gift.domain.Category;
import gift.domain.Product;
import gift.dto.OptionDTO;
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
}
