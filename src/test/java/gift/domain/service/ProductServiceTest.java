package gift.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import gift.domain.dto.request.ProductRequest;
import gift.domain.dto.response.CategoryResponse;
import gift.domain.dto.response.ProductResponse;
import gift.domain.entity.Category;
import gift.domain.entity.Product;
import gift.domain.exception.ProductAlreadyExistsException;
import gift.domain.exception.ProductNotFoundException;
import gift.domain.repository.ProductRepository;
import gift.utilForTest.MockObjectSupplier;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryService categoryService;

    private Product product;
    private Category category;
    private CategoryResponse categoryResponse;

    @BeforeEach
    public void beforeEach() {
        product = MockObjectSupplier.get(Product.class);
        category = MockObjectSupplier.get(Category.class);
        categoryResponse = CategoryResponse.of(category);
    }

    @Test
    @DisplayName("상품 얻기 - 성공")
    void getProductById() {
        //given
        given(productRepository.findById(any(Long.class))).willReturn(Optional.of(product));

        //when
        Product result = productService.getProductById(product.getId());

        //then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(product);
    }

    @Test
    @DisplayName("상품 얻기 - 실패")
    void getProductById_Fail() {
        //given
        given(productRepository.findById(any(Long.class))).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> productService.getProductById(product.getId()))
            .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("모든 상품 얻기")
    void getAllProducts() {
        //given
        List<Product> productList = List.of(
            new Product("name1", 1000, "image1.png", category),
            new Product("name2", 2000, "image2.png", category),
            new Product("name3", 3000, "image3.png", category));
        ReflectionTestUtils.setField(productList.get(0), "id", 1L);
        ReflectionTestUtils.setField(productList.get(1), "id", 2L);
        ReflectionTestUtils.setField(productList.get(2), "id", 3L);
        List<ProductResponse> expected = List.of(
            new ProductResponse(1L, "name1", 1000, "image1.png", categoryResponse),
            new ProductResponse(2L, "name2", 2000, "image2.png", categoryResponse),
            new ProductResponse(3L, "name3", 3000, "image3.png", categoryResponse));
        given(productRepository.findAll()).willReturn(productList);

        //when
        List<ProductResponse> actual = productService.getAllProducts();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("상품 추가 - 성공")
    void addProduct() {
        //given
        ProductRequest request = ProductRequest.of(product);
        ProductResponse expected = ProductResponse.of(product);
        given(productRepository.findByContents(any(ProductRequest.class))).willReturn(Optional.empty());
        given(productRepository.save(any(Product.class))).willReturn(product);
        given(categoryService.findById(eq(request.categoryId()))).willReturn(category);

        //when
        ProductResponse actual = productService.addProduct(request);

        //then
        assertThat(actual).isEqualTo(expected);
        then(productRepository).should(times(1)).findByContents(eq(request));
        then(productRepository).should(times(1)).save(any(Product.class));
        then(categoryService).should(times(1)).findById(eq(request.categoryId()));
    }

    @Test
    @DisplayName("상품 추가 - 이미 있는 상품 등록시")
    void addProduct_AlreadyExists() {
        //given
        ProductRequest request = new ProductRequest("name", 1000,"image.png", 1L);
        given(productRepository.findByContents(any(ProductRequest.class)))
            .willReturn(Optional.of(product));

        //when & then
        assertThatThrownBy(() -> productService.addProduct(request)).isInstanceOf(ProductAlreadyExistsException.class);
        then(productRepository).should(times(1)).findByContents(eq(request));
        then(productRepository).should(never()).save(any(Product.class));
    }

    @Test
    @DisplayName("상품 업데이트")
    void updateProductById() {
        //given
        Long id = 1L;
        ProductRequest request = new ProductRequest("newName", 10000, "newImage.jpg", 1L);
        given(productRepository.findById(any(Long.class))).willReturn(Optional.of(product));
        given(categoryService.findById(eq(request.categoryId()))).willReturn(category);
        ProductResponse expected = new ProductResponse(1L, "newName", 10000, "newImage.jpg", categoryResponse);

        //when
        ProductResponse actual = productService.updateProductById(id, request);

        //then
        assertThat(actual).isEqualTo(expected);
        then(productRepository).should(times(1)).findById(eq(id));
        then(categoryService).should(times(1)).findById(eq(request.categoryId()));
    }
}