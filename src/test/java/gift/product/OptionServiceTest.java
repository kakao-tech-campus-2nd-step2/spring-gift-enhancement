package gift.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gift.category.model.dto.Category;
import gift.product.model.OptionRepository;
import gift.product.model.ProductRepository;
import gift.product.model.dto.option.Option;
import gift.product.model.dto.product.Product;
import gift.product.service.OptionService;
import gift.user.model.dto.AppUser;
import gift.user.model.dto.Role;
import jakarta.persistence.EntityNotFoundException;
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
    private OptionRepository optionRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OptionService optionService;

    private Product product;
    private Option option;

    @BeforeEach
    public void setUp() {
        Category defaultCategory = new Category("기본", "기본 카테고리");
        AppUser defaultSeller = new AppUser("aabb@kakao.com", "1234", Role.USER, "aaaa");
        product = new Product("test", 100, "image", defaultSeller, defaultCategory);
        option = new Option("test", 10, 300, product);
    }

    @Test
    @DisplayName("상품이 존재한다면 옵션을 추가할 수 있다")
    public void addOption_ShouldAddOption_WhenProductExists() {
        // given
        when(productRepository.findByIdAndIsActiveTrue(product.getId())).thenReturn(Optional.of(product));

        // when
        optionService.addOption(option);

        // then
        verify(optionRepository).save(option);
    }

    @Test
    @DisplayName("상품이 존재하지 않는다면 EntityNotFoundException을 던진다")
    public void addOption_ShouldThrowEntityNotFoundException_WhenProductDoesNotExist() {
        // given
        when(productRepository.findByIdAndIsActiveTrue(any())).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> optionService.addOption(option))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Product not found");
    }
}
