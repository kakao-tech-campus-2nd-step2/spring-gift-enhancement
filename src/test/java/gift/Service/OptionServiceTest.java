package gift.Service;

import gift.DTO.OptionDTO;
import gift.Entity.OptionEntity;
import gift.Entity.ProductEntity;
import gift.Repository.OptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OptionServiceTest {

    @InjectMocks
    private OptionService optionService;

    @Mock
    private OptionRepository optionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOption() {
        OptionDTO optionDTO = new OptionDTO(1L, "Option1", 10L, 1L);
        OptionEntity optionEntity = new OptionEntity("Option1", 10L, new ProductEntity());
        when(optionRepository.save(any(OptionEntity.class))).thenReturn(optionEntity);

        OptionDTO createdOption = optionService.createOption(optionDTO);
        assertEquals(optionDTO.getName(), createdOption.getName());
        assertEquals(optionDTO.getQuantity(), createdOption.getQuantity());
    }

    @Test
    void testGetOptionById() {
        OptionEntity optionEntity = new OptionEntity("Option1", 10L, new ProductEntity());
        when(optionRepository.findById(1L)).thenReturn(Optional.of(optionEntity));

        OptionDTO optionDTO = optionService.getOptionById(1L);
        assertEquals(optionEntity.getName(), optionDTO.getName());
        assertEquals(optionEntity.getQuantity(), optionDTO.getQuantity());
    }

    @Test
    void testGetAllOptions() {
        List<OptionEntity> optionEntities = new ArrayList<>();
        optionEntities.add(new OptionEntity("Option1", 10L, new ProductEntity()));
        when(optionRepository.findAll()).thenReturn(optionEntities);

        List<OptionDTO> optionDTOs = optionService.getAllOptions();
        assertEquals(optionEntities.size(), optionDTOs.size());
    }

    @Test
    void testUpdateOption() {
        OptionDTO optionDTO = new OptionDTO(1L, "Option1", 10L, 1L);
        OptionEntity optionEntity = new OptionEntity("Option1", 10L, new ProductEntity());
        when(optionRepository.findById(1L)).thenReturn(Optional.of(optionEntity));
        when(optionRepository.save(any(OptionEntity.class))).thenReturn(optionEntity);

        OptionDTO updatedOption = optionService.updateOption(1L, optionDTO);
        assertEquals(optionDTO.getName(), updatedOption.getName());
        assertEquals(optionDTO.getQuantity(), updatedOption.getQuantity());
    }

    @Test
    void testDeleteOption() {
        doNothing().when(optionRepository).deleteById(1L);

        optionService.deleteOption(1L);
        verify(optionRepository, times(1)).deleteById(1L);
    }

    @Test
    void testValidateOptionNameUniqueness() {
        List<OptionEntity> optionEntities = new ArrayList<>();
        optionEntities.add(new OptionEntity("Option1", 10L, new ProductEntity()));
        when(optionRepository.findByProductId(1L)).thenReturn(optionEntities);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            optionService.createOption(new OptionDTO(1L, "Option1", 10L, 1L));
        });

        assertEquals("동일한 상품 내에서 옵션 이름이 중복될 수 없습니다.", exception.getMessage());
    }
}
