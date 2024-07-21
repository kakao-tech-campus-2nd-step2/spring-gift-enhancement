package gift.validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import gift.domain.Option;
import gift.domain.Product;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OptionValidatorTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private OptionValidator optionValidator;
    private Product product;
    private List<Option> options;

    @BeforeEach
    public void setup() {
        options = new ArrayList<>();
        Option option1 = new Option();
        option1.setId(1L);
        option1.setName("Option 1");
        option1.setQuantity(10);

        Option option2 = new Option();
        option2.setId(2L);
        option2.setName("Option 2");
        option2.setQuantity(20);

        options.add(option1);
        options.add(option2);

        product = new Product();
        product.setId(1L);
        product.setOptions(option1);
        product.setOptions(option2);
    }

    @Test
    public void 정상적인_옵션_이름() {
        assertDoesNotThrow(() -> optionValidator.validateOptionName("Valid Option Name"));
    }

    @Test
    public void 공백_옵션_이름() {
        // 옵션 이름이 null인 경우
        assertThrows(IllegalArgumentException.class, () -> optionValidator.validateOptionName(null));
        // 옵션 이름이 공백인 경우
        assertThrows(IllegalArgumentException.class, () -> optionValidator.validateOptionName("   "));
    }

    @Test
    public void 허용된_길이_초과_옵션_이름() {
        assertThrows(IllegalArgumentException.class, () -> optionValidator.validateOptionName("a".repeat(51)));
    }

    @Test
    public void 허용되지않은_옵션_이름() {
        assertThrows(IllegalArgumentException.class, () -> optionValidator.validateOptionName("Invalid Option!@#"));
    }

    @Test
    public void 중복되지않은_옵션_이름() {
        assertDoesNotThrow(() -> optionValidator.validateDuplicateOptionName(options, "New Option"));
    }

    @Test
    public void 중복된_옵션_이름() {
        assertThrows(IllegalArgumentException.class, () -> optionValidator.validateDuplicateOptionName(options, "Option 1"));
    }

    @Test
    public void 유효한_옵션_수량() {
        assertDoesNotThrow(() -> optionValidator.validateOptionQuantity(500));
    }

    @Test
    public void 유효하지않은_옵션_수량() {
        // 음수
        assertThrows(IllegalArgumentException.class, () -> optionValidator.validateOptionQuantity(-1));
        // 최대 범위 초과
        assertThrows(IllegalArgumentException.class, () -> optionValidator.validateOptionQuantity(100000001));
    }

    @Test
    public void 유효한_옵션_개수() {
        // 상품에 두 개 이상의 옵션이 있는 경우
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        assertDoesNotThrow(() -> optionValidator.validateNumberOfOptions(1L));
    }

    @Test
    public void 유효하지않은_옵션_개수() {
        // 상품에 하나의 옵션만 있는 경우
        product.getOptions().remove(1);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        assertThrows(IllegalArgumentException.class, () -> optionValidator.validateNumberOfOptions(1L));
    }

}