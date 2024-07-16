package gift.service;

import gift.dto.InputProductDTO;
import gift.model.Category;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

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
    @DisplayName("해당 id 갖는 product 반환")
    void getProductByIdTest() {
        // given
        given(productRepository.findById(any()))
                .willReturn(Optional.of(new Product(1L,"product", 1000, "image.url", null)));
        // when
        productService.getProductById(1L);
        // then
        then(productRepository).should().findById(1L);
    }

    @Test
    @DisplayName("product 저장")
    void saveProductTest() {
        // given
        given(categoryRepository.findByName(any()))
                .willReturn(Optional.of(new Category("교환권")));
        // when
        InputProductDTO inputProductDTO = new InputProductDTO("product", 1000, "image.url", "교환권");
        productService.saveProduct(inputProductDTO);
        // then
        then(productRepository).should().save(any());
    }

    @Test
    @DisplayName("product 삭제")
    void deleteProductTest() {
        // given
        Long productId = 1L;
        // when
        productService.deleteProduct(productId);
        // then
        then(productRepository).should().deleteById(productId);
    }

    @Test
    @DisplayName("product 업데이트")
    void updateProductTest() {
        // given
        InputProductDTO inputProductDTO = new InputProductDTO("updatedProduct", 2000, "image.url", "교환권");
        Category category = new Category("교환권");
        given(productRepository.findById(1L))
                .willReturn(Optional.of(new Product(1L, "Product", 1000, "image.url", category)));
        given(categoryRepository.findByName(any()))
                .willReturn(Optional.of(category));
        // when
        productService.updateProduct(1L, inputProductDTO);
        // then
        then(productRepository).should().findById(1L);
        then(productRepository).should().save(any(Product.class));
    }
}