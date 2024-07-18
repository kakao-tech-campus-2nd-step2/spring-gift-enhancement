package gift.service;

import static gift.util.CategoryFixture.createCategory;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import gift.domain.Category;
import gift.domain.Product;
import gift.dto.ProductDTO;
import gift.repository.ProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private CategoryService categoryService;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Category category;

    @BeforeEach
    void setup() {
        category = createCategory();
    }

    @DisplayName("상품 추가")
    @Test
    void addProduct() {
        // given
        Product product = new Product(1L, "test", 1234, "testImage", category);
        given(categoryService.getCategory(anyLong())).willReturn(category.toDTO());
        given(productRepository.save(any(Product.class))).willReturn(product);

        // when
        ProductDTO actual = productService.addProduct(product.toDTO());

        // then
        assertThat(actual).isEqualTo(product.toDTO());
    }

    @DisplayName("id로 상품 찾기")
    @Test
    void getProduct() {
        // given
        long id = 1L;
        Product product = new Product(id, "test", 1234, "testImage", category);
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));

        // when
        ProductDTO actual = productService.getProduct(id);

        // then
        assertThat(actual).isEqualTo(product.toDTO());
    }

    @DisplayName("상품 수정")
    @Test
    void updateProduct() {
        // given
        long id = 1L;
        Product product = new Product(id, "test", 1234, "testImage", category);
        Product updatedProduct = new Product(id, "updatedTest", 12345, "updatedTestImage", category);
        given(categoryService.getCategory(anyLong())).willReturn(category.toDTO());
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(productRepository.save(any(Product.class))).willReturn(updatedProduct);

        // when
        ProductDTO actual = productService.updateProduct(id, updatedProduct.toDTO());

        // then
        assertThat(actual).isEqualTo(updatedProduct.toDTO());
    }

    @DisplayName("상품 삭제")
    @Test
    void deleteProduct() {
        // given
        long id = 1L;
        Product product = new Product(id, "test", 1234, "testImage", category);
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));

        // when
        productService.deleteProduct(id);

        // then
        then(productRepository).should().delete(any(Product.class));
    }
}
