package gift.service;

import gift.domain.Category;
import gift.domain.Product;
import gift.dto.request.ProductRequest;
import gift.exception.CategoryNotFoundException;
import gift.exception.ProductNotFoundException;
import gift.repository.category.CategorySpringDataJpaRepository;
import gift.repository.product.ProductSpringDataJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductSpringDataJpaRepository productRepository;

    @Mock
    private CategorySpringDataJpaRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private Product mockProduct;
    private Category mockCategory;

    @Test
    public void testRegisterProduct() {
        mockCategory = mock(Category.class);
        when(categoryRepository.findByName("패션")).thenReturn(Optional.of(mockCategory));

        ProductRequest productRequest = new ProductRequest("상의", 800, "상의.jpg", "패션");

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product registeredProduct = productService.register(productRequest);

        assertEquals("상의", registeredProduct.getName());
        assertEquals(800, registeredProduct.getPrice());
        assertEquals("상의.jpg", registeredProduct.getImageUrl());
        assertEquals(mockCategory, registeredProduct.getCategory());

        verify(categoryRepository, times(1)).findByName("패션");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testRegisterProductCategoryNotFound() {
        mockCategory = mock(Category.class);
        when(categoryRepository.findByName("패션")).thenReturn(Optional.of(mockCategory));

        ProductRequest productRequest = new ProductRequest("상의", 800, "상의.jpg", "패션");
        when(categoryRepository.findByName("패션")).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> productService.register(productRequest));

        verify(categoryRepository, times(1)).findByName("패션");
        verify(productRepository, never()).save(any());
    }

    @Test
    public void testGetProducts() {
        mockProduct = mock(Product.class);
        when(mockProduct.getName()).thenReturn("상의");

        Pageable pageable = Pageable.unpaged();
        Page<Product> mockPage = new PageImpl<>(Collections.singletonList(mockProduct));
        when(productRepository.findAll(pageable)).thenReturn(mockPage);

        Page<Product> products = productService.getProducts(pageable);

        assertNotNull(products);
        assertEquals(1, products.getTotalElements());
        assertEquals("상의", products.getContent().getFirst().getName());

        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testFindOneProduct() {
        mockProduct = mock(Product.class);

        Long productId = 1L;

        when(mockProduct.getName()).thenReturn("상의");
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        Product product = productService.findOne(productId);

        assertEquals("상의", product.getName());

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    public void testFindOneProductNotFound() {
        Long productId = 11L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.findOne(productId));

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    public void testUpdateProduct() {
        mockProduct = mock(Product.class);
        mockCategory = mock(Category.class);

        Long productId = 1L;
        ProductRequest productRequest = new ProductRequest("하의", 1200, "하의.jpg", "패션");

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        when(categoryRepository.findByName("패션")).thenReturn(Optional.of(mockCategory));

        when(mockProduct.getName()).thenReturn("하의");
        when(mockProduct.getPrice()).thenReturn(1200);
        when(mockProduct.getImageUrl()).thenReturn("하의.jpg");
        when(mockProduct.getCategory()).thenReturn(mockCategory);

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product updatedProduct = productService.update(productId, productRequest);

        assertEquals("하의", updatedProduct.getName());
        assertEquals(1200, updatedProduct.getPrice());
        assertEquals("하의.jpg", updatedProduct.getImageUrl());
        assertEquals(mockCategory, updatedProduct.getCategory());

        verify(productRepository, times(1)).findById(productId);
        verify(categoryRepository, times(1)).findByName("패션");
        verify(productRepository, times(1)).save(any(Product.class));
    }


    @Test
    public void testUpdateProductNotFound() {
        Long productId = 11L;
        ProductRequest productRequest = new ProductRequest("하의", 1200, "하의.jpg", "패션");
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.update(productId, productRequest));

        verify(productRepository, times(1)).findById(productId);
        verify(categoryRepository, never()).findByName(any());
        verify(productRepository, never()).save(any());
    }

    @Test
    public void testDeleteProduct() {
        Long productId = 1L;
        mockProduct = mock(Product.class);
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        Product deletedProduct = productService.delete(productId);

        assertEquals(mockProduct, deletedProduct);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).delete(mockProduct);
    }

    @Test
    public void testDeleteProductNotFound() {
        Long productId = 11L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.delete(productId));

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).delete(any());
    }
}
