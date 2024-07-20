package gift.service;

import gift.entity.Option;
import gift.entity.Product;
import gift.entity.ProductOption;
import gift.repository.OptionRepository;
import gift.repository.ProductOptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class OptionServiceMockTest {

    @Autowired
    private ProductService productService;
    @MockBean
    private OptionRepository optionRepository;
    @MockBean
    private ProductOptionRepository productOptionRepository;

    @Test
    void 상품에_옵션이_한개_이하_존재할_때() {
        // given
        ProductOption productOption = new ProductOption(new Product(), new Option(), "test");

        // when
        when(productOptionRepository.findByProductId(any()))
                .thenReturn(List.of(productOption));

        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> productService.deleteProductOption(1L, 1L));
    }
}
