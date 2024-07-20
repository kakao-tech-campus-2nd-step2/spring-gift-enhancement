package gift.option.service;

import gift.option.dto.OptionDto;
import gift.option.repository.OptionRepository;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class OptionServiceTest {

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private OptionService optionService;

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product("Test Product");
        productRepository.save(product);
    }

    @Test
    public void use_wring_text_in_optionName() {
        // Given
        String name = "wrong@Option#Name";
        OptionDto optionDto = new OptionDto(name, 10);

        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> optionService.addOptionToProduct(product.getId(), optionDto));

        // 예외 메시지가 출력되는지
        assertThat(exception.getMessage()).isEqualTo("옳지 않은 문자가 사용되었습니다.");
    }

    @Test
    public void find_by_noExit_id() {
        // Given
        Long nonExistentProductId = 9545669L;

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> productRepository.findById(nonExistentProductId)
                .orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다.")));

        // 예외 메시지가 출력되는지
        assertThat(exception.getMessage()).isEqualTo("해당 상품이 존재하지 않습니다.");
    }
}