package gift.category.service;

import gift.category.model.Category;
import gift.category.repository.CategoryRepository;
import gift.product.dto.ProductDto;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import gift.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void save_product_successful() {
        // Given
        Long categoryId = 1L;
        ProductDto productDto = new ProductDto("Shoes", 35000, "https://www.google.com", categoryId);
        Category category = new Category("Adidas");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // When
        productService.save(productDto);

        // Then
        verify(productRepository).save(any(Product.class));
    }

    @Test
    public void save_product_category_not_found() {
        // Given
        Long categoryId = 1L;
        ProductDto productDto = new ProductDto("Shoes", 35000, "https://www.google.com", categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> productService.save(productDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("카테고리를 찾을 수 없습니다.");
    }
}