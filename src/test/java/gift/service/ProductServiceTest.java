package gift.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import gift.category.CategoryRepository;
import gift.category.model.Category;
import gift.product.ProductRepository;
import gift.product.ProductService;
import gift.product.model.Product;
import gift.product.model.ProductRequestDto;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, categoryRepository);
    }

    @Test
    void getAllProductsTest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "id"));
        given(productRepository.findAll((Pageable) any())).willReturn(Page.empty());

        productService.getAllProducts(pageable);

        then(productRepository).should().findAll(pageable);
    }

    @Test
    void getProductTest() {
        Category category = new Category(1L, "test", "##test", "test.jpg", "test");
        given(productRepository.findById(any())).willReturn(Optional.of(new Product(1L, "test", 1000, "test.jpg", category)));

        productService.getProductById(1L);

        then(productRepository).should().findById(any());
    }

    @Test
    void insertProductTest() {
        Category category = new Category(1L, "test", "##test", "test.jpg", "test");
        given(categoryRepository.findById(any())).willReturn(Optional.of(category));
        given(productRepository.save(any())).willReturn(new Product(1L, "test", 1000, "test.jpg", category));

        productService.insertProduct(new ProductRequestDto("test", 1000, "test.jpg", 1L));

        then(productRepository).should().save(any());
    }

    @Test
    void updateProductTest() {
        //더티 체킹
    }

    @Test
    void deleteProductTest() {
        productService.deleteProductById(1L);

        then(productRepository).should().deleteById(any());
    }
}
