package gift.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import gift.domain.Category;
import gift.domain.Product;
import gift.dto.CreateProductDto;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private CreateProductDto createProductDto;

    @BeforeEach
    public void setUp() {
        Category category = new Category("Electronics");
        createProductDto = new CreateProductDto();
        createProductDto.setName("Test Product");
        createProductDto.setPrice(100);
        createProductDto.setImageUrl("http://image.url");
        createProductDto.setCategory(category);
        createProductDto.setOptions(Collections.emptyList());
    }

    @Test
    public void testCreateProduct() {
        // Arrange
        Product product = createProductDto.toProduct();
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Product createdProduct = productService.createProduct(createProductDto);

        // Assert
        verify(productRepository).save(any(Product.class));
        assertEquals(createProductDto.getName(), createdProduct.getName());
        assertEquals(createProductDto.getPrice(), createdProduct.getPrice());
        assertEquals(createProductDto.getImageUrl(), createdProduct.getImageUrl());
        assertEquals(createProductDto.getCategory(), createdProduct.getCategory());
    }
}
