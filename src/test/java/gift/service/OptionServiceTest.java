package gift.service;

import gift.domain.Category;
import gift.domain.Product;
import gift.domain.Option;
import gift.dto.OptionDTO;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OptionServiceTest {

    @Mock
    private OptionRepository optionRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OptionService optionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetOptionsByProductId() {
        Long productId = 1L;
        Product product = new Product("Test Product", 1000, "test.jpg", new Category("Test Category"));
        Option option1 = new Option("Option 1", 10, product);
        Option option2 = new Option("Option 2", 20, product);

        when(optionRepository.findByProductId(productId)).thenReturn(Arrays.asList(option1, option2));

        List<OptionDTO> options = optionService.getOptionsByProductId(productId);

        assertEquals(2, options.size());
        assertEquals("Option 1", options.get(0).getName());
        assertEquals(10, options.get(0).getQuantity());
        assertEquals("Option 2", options.get(1).getName());
        assertEquals(20, options.get(1).getQuantity());
    }

    @Test
    public void testAddOption() {
        Long productId = 1L;
        Product product = new Product("Test Product", 1000, "test.jpg", new Category("Test Category"));
        OptionDTO optionDTO = new OptionDTO("Test Option", 10);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(optionRepository.existsByProductIdAndName(productId, optionDTO.getName())).thenReturn(false);
        when(optionRepository.save(any(Option.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OptionDTO savedOption = optionService.addOption(productId, optionDTO);

        assertNotNull(savedOption);
        assertEquals("Test Option", savedOption.getName());
        assertEquals(10, savedOption.getQuantity());
    }

    @Test
    public void testDeleteOption() {
        Long optionId = 1L;

        doNothing().when(optionRepository).deleteById(optionId);

        optionService.deleteOption(optionId);

        verify(optionRepository, times(1)).deleteById(optionId);
    }
}
