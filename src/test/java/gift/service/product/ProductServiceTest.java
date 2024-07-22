package gift.service.product;

import gift.domain.category.Category;
import gift.domain.product.Product;
import gift.repository.category.CategoryRepository;
import gift.repository.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("모든 제품을 조회할 수 있어야 한다")
    void getAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Product 1", 1000L, "Description 1", "image1.jpg", null));
        productList.add(new Product("Product 2", 2000L, "Description 2", "image2.jpg", null));
        Page<Product> page = new PageImpl<>(productList);

        when(productRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<Product> products = productService.getAllProducts(PageRequest.of(0, 10));

        assertEquals(2, products.getTotalElements());
        assertEquals("Product 1", products.getContent().get(0).getName());
        assertEquals("Product 2", products.getContent().get(1).getName());
    }

    @Test
    @DisplayName("존재하지 않는 제품 ID로 조회 시 예외가 발생해야 한다")
    void getProductById_ProductNotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> productService.getProductById(productId));
    }

    @Test
    @DisplayName("제품을 정상적으로 추가할 수 있어야 한다")
    void addProduct() {
        Product product = new Product("New Product", 1500L, "New Description", "new_image.jpg", null);
        Long categoryId = 1L;
        Category category = mock(Category.class);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        productService.addProduct(product, categoryId);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("유효하지 않은 제품 이름으로 추가 시 예외가 발생해야 한다")
    void addProduct_InvalidProductName() {
        Product product = new Product(null, 1500L, "New Description", "new_image.jpg", null);
        Long categoryId = 1L;
        assertThrows(IllegalArgumentException.class, () -> productService.addProduct(product, categoryId));
    }

    @Test
    @DisplayName("제품을 정상적으로 업데이트할 수 있어야 한다")
    void updateProduct() {
        Product product = new Product("Updated Product", 2000L, "Updated Description", "updated_image.jpg", null);
        Long categoryId = 1L;
        Category category = mock(Category.class);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        productService.updateProduct(product, categoryId);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("유효하지 않은 제품 이름으로 업데이트 시 예외가 발생해야 한다")
    void updateProduct_InvalidProductName() {
        Product product = new Product(null, 2000L, "Updated Description", "updated_image.jpg", null);
        Long categoryId = 1L;
        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(product, categoryId));
    }

    @Test
    @DisplayName("제품을 정상적으로 삭제할 수 있어야 한다")
    void deleteProduct() {
        Long productId = 1L;
        productService.deleteProduct(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }
}
