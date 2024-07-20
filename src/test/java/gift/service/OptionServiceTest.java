package gift.service;

import gift.dto.OptionRequest;
import gift.exception.AlreadyExistsException;
import gift.exception.NotFoundException;
import gift.model.Option;
import gift.model.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OptionServiceTest {

    @Mock
    private OptionRepository optionRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OptionService optionService;

    private Product product;
    private Option option;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product("name", 1000,"https://asdf");
        option = new Option("OptionName", 10L, product);
        productRepository.save(product);
        optionRepository.save(option);
    }

    @Test
    @DisplayName("getOptionByProductId 성공 테스트")
    public void testGetOptionByProductId_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        List<Option> options = optionService.getOptionByProductId(1L);

        assertEquals(1, options.size());
        assertEquals(product.getOptions().get(0), options.get(0));
    }

    @Test
    @DisplayName("getOptionByProductId 실패 테스트")
    public void testGetOptionByProductId_ProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> optionService.getOptionByProductId(1L));
        assertEquals("해당 id의 상품이 존재하지 않습니다.", thrown.getMessage());
    }

    @Test
    @Transactional
    @DisplayName("makeOption 성공 테스트")
    public void testMakeOption_Success() {
        OptionRequest request = new OptionRequest(1L,"상품옵션", 123L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        optionService.makeOption(1L, request);

        verify(optionRepository, times(2)).save(any(Option.class));
    }

    @Test
    @Transactional
    @DisplayName("makeOption 중복 이름 테스트")
    public void testMakeOption_DuplicateOptionName() {
        OptionRequest request = new OptionRequest(1L,"OptionName", 123L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(optionRepository.save(any(Option.class))).thenThrow(new AlreadyExistsException("해당 상품에 등록된 옵션과 동일한 이름이 존재합니다."));

        AlreadyExistsException thrown = assertThrows(AlreadyExistsException.class, () -> optionService.makeOption(1L, request));
        assertEquals("해당 상품에 등록된 옵션과 동일한 이름이 존재합니다.", thrown.getMessage());
    }

    @Test
    @DisplayName("makeOption 상품 못찾음 테스트")
    public void testMakeOption_ProductNotFound() {
        OptionRequest request = new OptionRequest(1L,"name3", 123L);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> optionService.makeOption(3L, request));
        assertEquals("해당 id의 상품이 존재하지 않습니다.", thrown.getMessage());
    }
}