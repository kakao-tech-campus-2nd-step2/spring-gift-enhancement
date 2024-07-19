package gift;

import gift.domain.Category;
import gift.domain.Option;
import gift.domain.Product;
import gift.dto.OptionDto;
import gift.repository.CategoryRepository;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import gift.service.OptionService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OptionServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private OptionRepository optionRepository;

    @InjectMocks
    private OptionService optionService;

    private Category category;
    private Product product;

    @BeforeEach
    public void setUp() {
        category = new Category("category", "color", "image", "");
        product = new Product(5L, "Test Product", 100, "image.jpg", category);
    }

    @Test
    public void testGetOptionsByProductId() {
        Option option = new Option("Test Option", 10, product);
        List<Option> options = new ArrayList<>();
        options.add(option);
        when(optionRepository.findAllByProductId(product.getId())).thenReturn(options);

        List<OptionDto> optionDtoList = optionService.getOptionsByProductId(product.getId());

        assertEquals(1, optionDtoList.size());
        assertEquals("Test Option", optionDtoList.get(0).getName());
        assertEquals(10, optionDtoList.get(0).getQuantity());
    }

    @Test
    public void testSaveOption_ProductNotFound() {
        OptionDto optionDto = new OptionDto(1L, "Test Option", 100);
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

        assertThatExceptionOfType(NoSuchElementException.class)
            .isThrownBy(()->optionService.saveOption(product.getId(), optionDto));
    }

    @Test
    public void testSaveOption_DuplicateOptionName() {
        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));

        Option existingOption = new Option("Test Option", 5, product);
        List<Option> options = new ArrayList<>();
        options.add(existingOption);
        given(optionRepository.findAllByProductId(product.getId())).willReturn(options);

        OptionDto optionDto = new OptionDto("Test Option", 10);

        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(()->optionService.saveOption(product.getId(), optionDto));
    }
}
