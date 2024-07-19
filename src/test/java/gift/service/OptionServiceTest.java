package gift.service;

import static gift.util.constants.OptionConstants.OPTION_NOT_FOUND;
import static gift.util.constants.ProductConstants.PRODUCT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gift.dto.option.OptionCreateRequest;
import gift.dto.option.OptionResponse;
import gift.dto.option.OptionUpdateRequest;
import gift.exception.option.OptionNotFoundException;
import gift.exception.product.ProductNotFoundException;
import gift.model.Category;
import gift.model.Option;
import gift.model.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class OptionServiceTest {

    private OptionRepository optionRepository;
    private ProductRepository productRepository;
    private OptionService optionService;

    @BeforeEach
    public void setUp() {
        optionRepository = Mockito.mock(OptionRepository.class);
        productRepository = Mockito.mock(ProductRepository.class);
        optionService = new OptionService(optionRepository, productRepository);
    }

    @Test
    @DisplayName("상품 ID로 옵션 조회")
    public void testGetOptionsByProductId() {
        Category category = new Category("Category", "#000000", "imageUrl", "description");
        Product product = new Product(1L, "Product", 100, "imageUrl", category, List.of());
        Option option = new Option(1L, "Option1", 100, product);
        product.update("Product", 100, "imageUrl", category, List.of(option));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        List<OptionResponse> options = optionService.getOptionsByProductId(1L);
        assertEquals(1, options.size());
        assertEquals("Option1", options.getFirst().name());
    }

    @Test
    @DisplayName("존재하지 않는 상품 ID로 옵션 조회")
    public void testGetOptionsByProductIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            optionService.getOptionsByProductId(1L);
        });

        assertEquals(PRODUCT_NOT_FOUND + 1, exception.getMessage());
    }

    @Test
    @DisplayName("옵션 ID로 조회")
    public void testGetOptionById() {
        Category category = new Category("Category", "#000000", "imageUrl", "description");
        Product product = new Product(1L, "Product", 100, "imageUrl", category, List.of());
        Option option = new Option(1L, "Option1", 100, product);

        when(optionRepository.findById(1L)).thenReturn(Optional.of(option));

        OptionResponse optionResponse = optionService.getOptionById(1L, 1L);
        assertEquals("Option1", optionResponse.name());
    }

    @Test
    @DisplayName("존재하지 않는 옵션 ID로 조회")
    public void testGetOptionByIdNotFound() {
        when(optionRepository.findById(1L)).thenReturn(Optional.empty());

        OptionNotFoundException exception = assertThrows(OptionNotFoundException.class, () -> {
            optionService.getOptionById(1L, 1L);
        });

        assertEquals(OPTION_NOT_FOUND + 1, exception.getMessage());
    }

    @Test
    @DisplayName("상품에 옵션 추가")
    public void testAddOptionToProduct() {
        Category category = new Category("Category", "#000000", "imageUrl", "description");
        Product product = new Product(1L, "Product", 100, "imageUrl", category, List.of());
        Option option = new Option(1L, "Option1", 100, product);
        OptionCreateRequest optionCreateRequest = new OptionCreateRequest("Option1", 100);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(optionRepository.save(any(Option.class))).thenReturn(option);

        OptionResponse createdOption = optionService.addOptionToProduct(1L, optionCreateRequest);
        assertEquals("Option1", createdOption.name());
    }

    @Test
    @DisplayName("존재하지 않는 상품에 옵션 추가")
    public void testAddOptionToProductNotFound() {
        OptionCreateRequest optionCreateRequest = new OptionCreateRequest("Option1", 100);

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            optionService.addOptionToProduct(1L, optionCreateRequest);
        });

        assertEquals(PRODUCT_NOT_FOUND + 1, exception.getMessage());
    }

    @Test
    @DisplayName("옵션 업데이트")
    public void testUpdateOption() {
        Category category = new Category("Category", "#000000", "imageUrl", "description");
        Product product = new Product(1L, "Product", 100, "imageUrl", category, List.of());
        Option existingOption = new Option(1L, "Old Option", 100, product);
        Option updatedOption = new Option(1L, "Updated Option", 200, product);
        OptionUpdateRequest optionUpdateRequest = new OptionUpdateRequest("Updated Option", 200);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(optionRepository.findById(1L)).thenReturn(Optional.of(existingOption));
        when(optionRepository.save(any(Option.class))).thenReturn(updatedOption);

        OptionResponse result = optionService.updateOption(1L, 1L, optionUpdateRequest);
        assertEquals("Updated Option", result.name());
        assertEquals(200, result.quantity());
    }

    @Test
    @DisplayName("존재하지 않는 옵션 ID로 업데이트")
    public void testUpdateOptionNotFound() {
        OptionUpdateRequest optionUpdateRequest = new OptionUpdateRequest("Updated Option", 200);

        when(optionRepository.findById(1L)).thenReturn(Optional.empty());

        OptionNotFoundException exception = assertThrows(OptionNotFoundException.class, () -> {
            optionService.updateOption(1L, 1L, optionUpdateRequest);
        });

        assertEquals(OPTION_NOT_FOUND + 1, exception.getMessage());
    }

    @Test
    @DisplayName("옵션 삭제")
    public void testDeleteOption() {
        Category category = new Category("Category", "#000000", "imageUrl", "description");
        Product product = new Product(1L, "Product", 100, "imageUrl", category, List.of());
        Option option = new Option(1L, "Option1", 100, product);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(optionRepository.findById(1L)).thenReturn(Optional.of(option));
        doNothing().when(optionRepository).delete(option);

        optionService.deleteOption(1L, 1L);
        verify(optionRepository, times(1)).delete(option);
    }

    @Test
    @DisplayName("존재하지 않는 옵션 ID로 삭제")
    public void testDeleteOptionNotFound() {
        when(optionRepository.findById(1L)).thenReturn(Optional.empty());

        OptionNotFoundException exception = assertThrows(OptionNotFoundException.class, () -> {
            optionService.deleteOption(1L, 1L);
        });

        assertEquals(OPTION_NOT_FOUND + 1, exception.getMessage());
    }
}
