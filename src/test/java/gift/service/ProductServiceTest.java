package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gift.dto.ProductDto;
import gift.exception.NonIntegerPriceException;
import gift.model.Category;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDto productDto;
    private Category category;

    @BeforeEach
    void setUp() {
<<<<<<< HEAD
        category = new Category("상품권");
        category.setId(1L);
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
=======
>>>>>>> 54b1157 (chore: 파일 포매팅)
        var byId = categoryRepository.findById(1L);
        category = byId.get();
        product = new Product(1L, "productName", 10000, "image.jpg", category);
        productDto = new ProductDto(product.getName(), product.getPrice(), product.getImageUrl(),
            product.getCategory().getId());
    }


    @Test
    @DisplayName("상품 저장 후 전체 상품 조회")
    public void saveAndGetAllProductsTest() throws NonIntegerPriceException {
        // given
        given(productRepository.save(product)).willReturn(product);
        var productList = Collections.singletonList(product);
        given(productRepository.findAll()).willReturn(productList);



        // when
        Product savedProduct = productService.createProduct(productDto);  // 실제 저장 호출
        var allProducts = productService.getAllProducts();

        // then
        assertThat(savedProduct).isEqualTo(product);
        assertThat(allProducts).isEqualTo(productList);
    }

    @Test
    @DisplayName("상품 ID 탐색")
    public void saveAndGetProductByIDTest() throws Exception {
        // given
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        // when
        Product foundProduct = productService.getProductById(product.getId());

        // then
        assertThat(foundProduct).isEqualTo(product);

        // verify
        verify(productRepository, times(1)).findById(product.getId());
    }

    @Test
    @DisplayName("상품 생성 테스트")
    public void createProductAndSaveTest() throws NonIntegerPriceException {
        // given
        when(productRepository.save(product)).thenReturn(product);

        // when
        var savedProduct = productService.createProduct(productDto);

        // then
        assertThat(savedProduct).isEqualTo(product);
    }

    @Test
    @DisplayName("상품 업데이트 테스트")
    public void updateProductTest() throws Exception {
        // given
        when(productRepository.save(product)).thenReturn(product);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        var newProduct = new Product("newName", 20000, "newimage.jpg");
        newProduct.setId(1L);

        // when
        var updatedProduct = productService.updateProduct(newProduct);

        // then
        assertThat(updatedProduct.getId()).isEqualTo(product.getId());

        // verify
        verify(productRepository, times(1)).save(newProduct);
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    public void deleteProductTest() throws Exception {
        // given
        doNothing().when(productRepository).deleteById(product.getId());

        // when
        var isDeleted = productService.deleteProduct(product.getId());

        // then
        assertThat(isDeleted).isTrue();

        // verify
        verify(productRepository, times(1)).deleteById(product.getId());
    }
}