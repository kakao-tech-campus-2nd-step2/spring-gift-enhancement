package gift.service;

import gift.domain.Category;
import gift.domain.Product;
import gift.dto.request.ProductRequest;
import gift.exception.CategoryNotFoundException;
import gift.exception.InvalidProductDataException;
import gift.exception.ProductNotFoundException;
import gift.repository.category.CategorySpringDataJpaRepository;
import gift.repository.product.ProductSpringDataJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
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

    private List<Product> mockProducts;
    private List<Category> mockCategories;

    @BeforeEach
    public void setUp() {
        mockCategories = new ArrayList<>();
        mockCategories.add(new Category(1L, "패션"));
    }

    @Test
    public void testRegisterProduct() {
        ProductRequest productRequest = new ProductRequest("상의", 800, "상의.jpg", "패션");
        Category mockCategory = mockCategories.getFirst();
        when(categoryRepository.findByName("패션")).thenReturn(Optional.of(mockCategory));

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
        ProductRequest productRequest = new ProductRequest("상의", 800, "상의.jpg", "패션");
        when(categoryRepository.findByName("패션")).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> productService.register(productRequest));

        verify(categoryRepository, times(1)).findByName("패션");
        verify(productRepository, never()).save(any());
    }

    @Test
    public void testGetProducts() {
        Pageable pageable = Pageable.unpaged();
        when(productRepository.findAll(pageable)).thenReturn(Page.empty());

        Page<Product> products = productService.getProducts(pageable);

        assertNotNull(products);
        assertEquals(0, products.getTotalElements());

        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testFindOneProduct() {
        mockProducts = new ArrayList<>();
        mockProducts.add(new Product(1L, "상의", 1000, "상의.jpg", mockCategories.getFirst()));

        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProducts.getFirst()));

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
        mockProducts = new ArrayList<>();
        mockProducts.add(new Product(1L, "상의", 1000, "상의.jpg", mockCategories.getFirst()));

        Long productId = 1L;
        ProductRequest productRequest = new ProductRequest("하의", 1200, "하의.jpg", "패션");
        Category mockCategory = mockCategories.getFirst();
        Product existingProduct = mockProducts.getFirst();
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findByName("패션")).thenReturn(Optional.of(mockCategory));

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
        Product existingProduct = mockProducts.getFirst();
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        Product deletedProduct = productService.delete(productId);

        assertEquals(existingProduct, deletedProduct);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).delete(existingProduct);
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
