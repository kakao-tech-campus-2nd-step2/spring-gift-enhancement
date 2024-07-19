package gift;

import gift.model.ProductOption;
import gift.repository.ProductOptionRepository;
import gift.service.ProductOptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ProductOptionServiceTest {

    @Mock
    private ProductOptionRepository productOptionRepository;

    @InjectMocks
    private ProductOptionService productOptionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById() {
        ProductOption productOption = new ProductOption();
        productOption.setId(1L);
        productOption.setName("Option 1");

        when(productOptionRepository.findById(anyLong())).thenReturn(Optional.of(productOption));

        ProductOption result = productOptionService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Option 1", result.getName());
        verify(productOptionRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdNotFound() {
        when(productOptionRepository.findById(anyLong())).thenReturn(Optional.empty());

        ProductOption result = productOptionService.findById(1L);

        assertNull(result);
        verify(productOptionRepository, times(1)).findById(1L);
    }
}