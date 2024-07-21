package gift.service;

import gift.domain.Option;
import gift.repository.OptionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OptionServiceTest {

    @Mock
    private OptionRepository optionRepository;

    @InjectMocks
    private OptionService optionService;

    private Option option;

    @BeforeEach
    public void setUp() {
        option = new Option("Test Option", null);
        option.setId(1L);
        option.setQuantity(10);
    }

    @Test
    public void testDecreaseQuantitySuccess() {
        // Arrange
        when(optionRepository.findById(1L)).thenReturn(Optional.of(option));

        // Act
        optionService.decreaseQuantity(1L, 5);

        // Assert
        verify(optionRepository).findById(1L);
        verify(optionRepository).save(option);
        assert(option.getQuantity() == 5);
    }

    @Test
    public void testDecreaseQuantityThrowsEntityNotFoundException() {
        // Arrange
        when(optionRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> optionService.decreaseQuantity(1L, 5));
        verify(optionRepository).findById(1L);
        verify(optionRepository, never()).save(any(Option.class));
    }

    @Test
    public void testDecreaseQuantityThrowsIllegalArgumentExceptionForNegativeAmount() {
        // Arrange
        when(optionRepository.findById(1L)).thenReturn(Optional.of(option));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> optionService.decreaseQuantity(1L, -5));
        verify(optionRepository).findById(1L);
        verify(optionRepository, never()).save(option);
    }

    @Test
    public void testDecreaseQuantityThrowsIllegalArgumentExceptionForInsufficientQuantity() {
        // Arrange
        when(optionRepository.findById(1L)).thenReturn(Optional.of(option));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> optionService.decreaseQuantity(1L, 15));
        verify(optionRepository).findById(1L);
        verify(optionRepository, never()).save(option);
    }
}
