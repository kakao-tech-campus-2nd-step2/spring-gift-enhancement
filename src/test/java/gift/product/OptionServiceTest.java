package gift.product;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gift.category.model.dto.Category;
import gift.product.model.OptionRepository;
import gift.product.model.dto.option.Option;
import gift.product.model.dto.product.Product;
import gift.product.service.OptionService;
import gift.product.service.ProductService;
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
    private ProductService productService;

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
        when(productService.findProduct(product.getId())).thenReturn(Optional.of(product));

        // when
        optionService.addOption(option);

        // when, then
        assertDoesNotThrow(() -> optionService.addOption(option));
        verify(optionRepository, times(1)).save(any(Option.class));
    }

    @Test
    @DisplayName("상품이 존재하지 않는다면 EntityNotFoundException을 던진다")
    public void addOption_ShouldThrowEntityNotFoundException_WhenProductDoesNotExist() {
        // given
        when(productService.findProduct(any())).thenThrow(new EntityNotFoundException("Product"));

        // when, then
        assertThrows(EntityNotFoundException.class, () -> optionService.addOption(option));
        verify(optionRepository, times(0)).save(any(Option.class));
    }

    @Test
    @DisplayName("수정 요청 시 옵션이 존재할 때 옵션을 수정할 수 있다")
    public void updateOption_ShouldUpdateOption_WhenProductExists() {
        // given
        String newName = "수정";
        option.setName(newName);
        when(productService.findProduct(product.getId())).thenReturn(product);
        when(optionService.findOption(option.getId())).thenReturn(option);

        // when, then
        assertDoesNotThrow(() -> optionService.updateOption(option));
        verify(optionRepository, times(1)).save(any(Option.class));
        assertEquals(newName, option.getName());
    }

    @Test
    @DisplayName("수정 요청 시 옵션이 존재하지 않으면 EntityNotFoundException을 던진다.")
    public void updateOptionShouldThrowEntityNotFoundException_WhenOptionNotExists() {
        // given
        when(productService.findProduct(product.getId())).thenReturn(product);
        when(optionService.findOption(option.getId())).thenThrow(new EntityNotFoundException("Option"));
        ;

        // when,then
        assertThrows(EntityNotFoundException.class, () -> optionService.updateOption(option));
        verify(optionRepository, times(0)).save(any(Option.class));
    }

    @Test
    @DisplayName("삭제 요청 시 옵션이 존재할 때 옵션을 삭제할 수 있다.")
    public void deleteOption_ShouldDeleteOption_WhenProductExists() {
        // given
        when(productService.findProduct(product.getId())).thenReturn(product);
        when(optionService.findOption(option.getId())).thenReturn(option);

        // when
        optionService.deleteOption(option.getId());

        // then
        verify(optionRepository).deleteById(option.getId());
        // when,then
        assertDoesNotThrow(() -> optionService.deleteOption(option));
        verify(optionRepository, times(1)).save(any(Option.class));
        assertFalse(option.isActive());
    }

    @Test
    @DisplayName("삭제 요청 시 옵션이 존재하지 않으면 EntityNotFoundException을 던진다.")
    public void deleteOption_ShouldDeleteOption_WhenProductExists() {
        // given
        when(productService.findProduct(product.getId())).thenReturn(product);
        when(optionService.findOption(option.getId())).thenThrow(new EntityNotFoundException("Option"));

        // when,then
        assertThrows(EntityNotFoundException.class, () -> optionService.deleteOption(option));
        verify(optionRepository, times(0)).save(any(Option.class));
    }
}
