package gift.ServiceTest;

import gift.model.Option;
import gift.repository.OptionRepository;
import gift.service.OptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OptionServiceTest {

    @Mock
    private OptionRepository optionRepository;

    @InjectMocks
    private OptionService optionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void decreaseOptionQuantity() {
        Long optionId = 1L;
        Option option = new Option(optionId, "Test Option", 10);

        when(optionRepository.findById(optionId)).thenReturn(Optional.of(option));
        when(optionRepository.save(any(Option.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Option updatedOption = optionService.decreaseOptionQuantity(1L, 3);

        assertNotNull(updatedOption);
        assertEquals(7, updatedOption.getQuantity());

        verify(optionRepository, times(1)).findById(optionId);
        verify(optionRepository, times(1)).save(updatedOption);
    }
}
