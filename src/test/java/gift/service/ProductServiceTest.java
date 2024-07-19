package gift.service;

import gift.entity.Category;
import gift.entity.Product;
import gift.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    Category category = mock(Category.class);
    Product product = new Product("Product", 1000, "imageUrl.url", category);


    @Test
    @DisplayName("Save Test")
    void saveProduct() {
        //given
        when(productRepository.save(product)).thenReturn(product);

        //when
        productService.saveProduct(product);

        //then
        verify(productRepository).save(product);
    }

    @Test
    void getAllProducts() {
    }

    @Test
    @DisplayName("Id로 조회: 성공")
    void successGetProductById() {
        //given
        when(productRepository.findById((any()))).thenReturn(Optional.of(product));

        //when
        Product product = productService.getProductById(1L);

        //then
        assertAll(
                () -> assertThat(product.getName()).isEqualTo("Product"),
                () -> assertThat(product.getPrice()).isEqualTo(1000),
                () -> assertThat(product.getImageUrl()).isEqualTo("imageUrl.url"),
                () -> assertThat(product.getCategory()).isEqualTo(category)
        );

    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void getProductPage() {
    }
}