package gift.Product;

import gift.model.Option;
import gift.repository.OptionRepository;
import gift.service.OptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OptionServiceTest {
    @Mock
    private OptionRepository optionRepository;

    @InjectMocks
    private OptionService optionService;

    private Option option;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize an option with quantity 10 for testing
        option = new Option();
        option.setId(1L);
        option.setOptionName("Test Option");
        option.setQuantity(10L);
    }

    @Test
    public void testSubtractOptionSuccess() {
        when(optionRepository.save(any(Option.class))).thenReturn(option);

        Option updatedOption = optionService.subtractOption(option, 5L);

        assertNotNull(updatedOption);
        assertEquals(5L, updatedOption.getQuantity());
        verify(optionRepository, times(1)).save(any(Option.class));
    }

}
