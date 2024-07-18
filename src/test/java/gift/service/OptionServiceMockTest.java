package gift.service;

import gift.entity.Option;
import gift.entity.OptionDTO;
import gift.entity.Product;
import gift.entity.ProductOption;
import gift.repository.OptionRepository;
import gift.repository.ProductOptionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    void 같은_이름의_option이_product에_있을_때() {
        // given
        Option option = new Option(new OptionDTO("test", 1));
        when(optionRepository.findById(any()))
                .thenReturn(Optional.of(option));

        when(productOptionRepository.findByProductId(any()))
                .thenReturn(List.of(new ProductOption(new Product(), option)));


        // when
        // then
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> productService.addProductOption(1L, 1L));
    }

    @Test
    void 상품에_옵션이_한개_이하_존재할_때() {
        // given
        when(productOptionRepository.findByProductId(any()))
                .thenReturn(List.of(new ProductOption(new Product(), new Option())));

        // when
        // then
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> productService.deleteProductOption(1L, 1L));
    }
}
