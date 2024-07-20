package gift.service;

import static org.junit.jupiter.api.Assertions.*;

import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import gift.repository.WishProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private WishProductRepository wishProductRepository;

    @Mock
    private ProductOptionService productOptionService;

    @Test
    void createProduct() {
    }

    @Test
    void readProductById() {
    }

    @Test
    void readProductsByCategoryId() {
    }

    @Test
    void readAllProducts() {
    }

    @Test
    void testReadAllProducts() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}