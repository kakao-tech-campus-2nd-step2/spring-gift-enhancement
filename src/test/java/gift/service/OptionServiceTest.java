package gift.service;

import gift.domain.OptionDTO;
import gift.entity.Category;
import gift.entity.Options;
import gift.entity.Option;
import gift.entity.Product;
import gift.repository.OptionRepository;
import gift.repository.OptionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OptionServiceTest {

    @Mock
    private OptionsRepository optionsRepository;

    @Mock
    private OptionRepository optionRepository;

    @InjectMocks
    private OptionService optionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByProduct_Id() {
        // Given
        int productId = 1;
        Category testCategory = new Category("test", "test", "test", "test");
        Product product = new Product(testCategory,1, "test", "testURL");
        Options options = new Options(product, new ArrayList<>());
        when(optionsRepository.findByProduct_Id(productId)).thenReturn(Optional.of(options));

        // When
        Optional<Options> result = optionService.findByProduct_Id(productId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(options, result.get());
        verify(optionsRepository).findByProduct_Id(productId);
    }

    @Test
    void testAddOption() {
        // Given
        int productId = 1;
        Category testCategory = new Category("test", "test", "test", "test");
        Product product = new Product(testCategory,1, "test", "testURL");
        OptionDTO optionDTO = new OptionDTO("Option 1", 100);
        Option savedOption = new Option(optionDTO);
        Options options = new Options(product, new ArrayList<>());

        when(optionRepository.save(any(Option.class))).thenReturn(savedOption);
        when(optionsRepository.findByProduct_Id(productId)).thenReturn(Optional.of(options));
        when(optionsRepository.save(any(Options.class))).thenReturn(options);

        // When
        Option result = optionService.addOption(productId, optionDTO);

        // Then
        assertNotNull(result);
        assertEquals(savedOption, result);
        verify(optionRepository).save(any(Option.class));
        verify(optionsRepository).findByProduct_Id(productId);
        verify(optionsRepository).save(any(Options.class));
    }

    @Test
    void testAddOptionProductNotFound() {
        // Given
        int productId = 1;
        OptionDTO optionDTO = new OptionDTO("Option 1", 100);

        when(optionsRepository.findByProduct_Id(productId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> optionService.addOption(productId, optionDTO));
    }

    @Test
    void testUpdateOption() {
        // Given
        int productId = 1;
        int optionId = 1;
        Category testCategory = new Category("test", "test", "test", "test");
        Product product = new Product(testCategory,1, "test", "testURL");
        OptionDTO optionDTO = new OptionDTO("Updated Option", 150);
        Option updatedOption = new Option(optionId, optionDTO);
        Options options = new Options(product, new ArrayList<>());

        when(optionRepository.save(any(Option.class))).thenReturn(updatedOption);
        when(optionsRepository.findByProduct_Id(productId)).thenReturn(Optional.of(options));
        when(optionsRepository.save(any(Options.class))).thenReturn(options);

        // When
        Option result = optionService.updateOption(productId, optionId, optionDTO);

        // Then
        assertNotNull(result);
        assertEquals(updatedOption, result);
        verify(optionRepository).save(any(Option.class));
        verify(optionsRepository).findByProduct_Id(productId);
        verify(optionsRepository).save(any(Options.class));
    }

    @Test
    void testUpdateOptionProductNotFound() {
        // Given
        int productId = 1;
        int optionId = 1;
        OptionDTO optionDTO = new OptionDTO("Updated Option", 150);

        when(optionsRepository.findByProduct_Id(productId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> optionService.updateOption(productId, optionId, optionDTO));
    }

    @Test
    void testDeleteOption() {
        // Given
        int productId = 1;
        int optionId = 1;
        Category testCategory = new Category("test", "test", "test", "test");
        Product product = new Product(testCategory,1, "test", "testURL");
        Options options = new Options(product, new ArrayList<>());

        when(optionsRepository.findByProduct_Id(productId)).thenReturn(Optional.of(options));
        when(optionsRepository.save(any(Options.class))).thenReturn(options);

        // When
        assertDoesNotThrow(() -> optionService.deleteOption(productId, optionId));

        // Then
        verify(optionsRepository).findByProduct_Id(productId);
        verify(optionsRepository).save(any(Options.class));
    }

    @Test
    void testDeleteOptionProductNotFound() {
        // Given
        int productId = 1;
        int optionId = 1;

        when(optionsRepository.findByProduct_Id(productId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> optionService.deleteOption(productId, optionId));
    }
}