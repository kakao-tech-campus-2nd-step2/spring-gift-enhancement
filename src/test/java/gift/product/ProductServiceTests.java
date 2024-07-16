package gift.product;

import gift.core.domain.product.*;
import gift.core.domain.product.exception.ProductAlreadyExistsException;
import gift.core.domain.product.exception.ProductNotFoundException;
import gift.product.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    private ProductService productService;

    @BeforeEach
    public void setUp() {
        productService = new ProductServiceImpl(productRepository, productCategoryRepository);
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product(
                0L,
                "test",
                100,
                "test.jpg",
                ProductCategory.of("test")
        );

        when(productRepository.exists(0L)).thenReturn(false);
        when(productCategoryRepository.findByName("test")).thenReturn(Optional.of(ProductCategory.of("test")));

        productService.createProductWithCategory(product);
        verify(productRepository).save(product);
    }

    @Test
    public void testCreateProductWithExistingProduct() {
        Product product = new Product(
                0L,
                "test",
                100,
                "test.jpg",
                ProductCategory.of("test")
        );

        when(productRepository.exists(0L)).thenReturn(true);

        assertThrows(ProductAlreadyExistsException.class, () -> productService.createProductWithCategory(product));
    }

    @Test
    public void testGetProduct() {
        Long productId = 1L;
        Product product = new Product(
                productId,
                "test",
                100,
                "test.jpg",
                ProductCategory.of("test")
        );

        when(productRepository.exists(productId)).thenReturn(true);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.get(productId);
        verify(productRepository).findById(productId);
    }

    @Test
    public void testGetProductWithNonExistingProduct() {
        Long productId = 1L;
        when(productRepository.exists(productId)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.get(productId));
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product(
                0L,
                "test",
                100,
                "test.jpg",
                ProductCategory.of("test")
        );

        when(productRepository.exists(0L)).thenReturn(true);
        when(productCategoryRepository.findByName("test")).thenReturn(Optional.of(ProductCategory.of("test")));

        productService.updateProduct(product);
        verify(productRepository).save(product);
    }

    @Test
    public void testUpdateProductWithNonExistingProduct() {
        Product product = new Product(
                0L,
                "test",
                100, 
                "test.jpg",
                ProductCategory.of("test")
        );

        when(productRepository.exists(0L)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(product));
    }

    @Test
    public void testDeleteProductById() {
        Long productId = 1L;
        when(productRepository.exists(productId)).thenReturn(true);

        productService.remove(productId);
        verify(productRepository).remove(productId);
    }

    @Test
    public void testDeleteProductByIdWithNonExistingProduct() {
        Long productId = 1L;
        when(productRepository.exists(productId)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.remove(productId));
    }
}
