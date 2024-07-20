package gift.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

import gift.model.category.Category;
import gift.model.option.CreateOptionRequest;
import gift.model.option.CreateOptionResponse;
import gift.model.option.Option;
import gift.model.product.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OptionServiceTest {

    @InjectMocks
    private OptionService optionService;

    @Mock
    private OptionRepository optionRepository;
    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("옵션 등록")
    void save() {
        Category category = new Category(1L, "차량", "brown", "www.aaa.jpg", "차량 카테고리입니다.");
        Product product = new Product(1L, "상품1", 1000, "image1.jpg", category);
        Option option = new Option(null, "test", 5);
        CreateOptionRequest request = new CreateOptionRequest("test", 5);

        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(optionRepository.save(any(Option.class))).willReturn(option);

        optionService.register(1L, request);

        then(productRepository).should().findById(1L);
        then(optionRepository).should().save(any(Option.class));
    }
}
