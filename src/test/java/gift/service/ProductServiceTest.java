package gift.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.List;

import gift.domain.Category;
import gift.domain.Option;
import gift.domain.Product;
import gift.dto.CreateProductDto;
import gift.dto.UpdateProductDto;
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
    private Product existingProduct;
    private UpdateProductDto updateProductDto;
    private Category category;

    @BeforeEach
    public void setUp() {
        // for createProduct test
        category = new Category("Electronics");
        createProductDto = new CreateProductDto();
        createProductDto.setName("Test Product");
        createProductDto.setPrice(100);
        createProductDto.setImageUrl("http://image.url");
        createProductDto.setCategory(category);
        createProductDto.setOptions(Collections.emptyList());

        // for updateProduct test
        existingProduct = new Product("Existing Product", 100, "http://image.url", category);
        existingProduct.setId(1L);
        Option existingOption = new Option("Existing Option", existingProduct);
        existingOption.setId(1L);
        existingProduct.setOptions(Collections.singletonList(existingOption));

        updateProductDto = new UpdateProductDto("Updated Product",150,  "http://newimage.url", category);

        Option updateOption = new Option("Updated Option", existingProduct);
        updateOption.setId(1L); // This is to simulate updating an existing option
        Option newOption = new Option("New Option", existingProduct);
        newOption.setId(2L); // This is to simulate adding a new option
        updateProductDto.setOptions(Arrays.asList(updateOption, newOption));
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

    @Test
    public void testUpdateProduct() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        // Act
        Product updatedProduct = productService.updateProduct(1L, updateProductDto);

        // Assert
        verify(productRepository).findById(1L);
        verify(productRepository).save(existingProduct);

        assertEquals(updateProductDto.getName(), updatedProduct.getName());
        assertEquals(updateProductDto.getPrice(), updatedProduct.getPrice());
        assertEquals(updateProductDto.getImageUrl(), updatedProduct.getImageUrl());
        assertEquals(updateProductDto.getCategory(), updatedProduct.getCategory());

        List<Option> updatedOptions = updatedProduct.getOptions();
        assertEquals(2, updatedOptions.size());
        assertEquals("Updated Option", updatedOptions.get(0).getName());
        assertEquals("New Option", updatedOptions.get(1).getName());
    }
}
