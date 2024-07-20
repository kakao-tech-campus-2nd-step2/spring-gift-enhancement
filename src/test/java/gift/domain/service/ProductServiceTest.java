package gift.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import gift.domain.dto.request.OptionRequest;
import gift.domain.dto.request.ProductAddRequest;
import gift.domain.dto.request.ProductUpdateRequest;
import gift.domain.dto.response.CategoryResponse;
import gift.domain.dto.response.OptionResponse;
import gift.domain.dto.response.ProductResponse;
import gift.domain.entity.Category;
import gift.domain.entity.Option;
import gift.domain.entity.Product;
import gift.domain.exception.conflict.ProductAlreadyExistsException;
import gift.domain.exception.notFound.ProductNotFoundException;
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

    @Mock
    private OptionService optionService;

    private Product product;
    private Category category;
    private List<Option> options;
    private CategoryResponse categoryResponse;
    private List<OptionRequest> optionRequests;
    private List<OptionResponse> optionResponses;

    @BeforeEach
    public void beforeEach() {
        product = MockObjectSupplier.get(Product.class);
        category = MockObjectSupplier.get(Category.class);
        options = List.of(MockObjectSupplier.get(Option.class));
        categoryResponse = CategoryResponse.of(category);
        optionRequests = OptionRequest.of(options);
        optionResponses = OptionResponse.of(options);
    }

    @Test
    @DisplayName("[UnitTest] 상품 얻기: 성공")
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
    @DisplayName("[UnitTest/Fail] 상품 얻기")
    void getProductById_Fail() {
        //given
        given(productRepository.findById(any(Long.class))).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> productService.getProductById(product.getId()))
            .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("[UnitTest] 모든 상품 얻기")
    void getAllProducts() {
        //given
        List<Product> productList = List.of(
            new Product("name1", 1000, "image1.png", category),
            new Product("name2", 2000, "image2.png", category),
            new Product("name3", 3000, "image3.png", category));
        for (int i = 0; i < productList.size(); i++) {
            ReflectionTestUtils.setField(productList.get(i), "id", i + 1L);
            ReflectionTestUtils.setField(productList.get(i), "options", options);
        }
        List<ProductResponse> expected = List.of(
            new ProductResponse(1L, "name1", 1000, "image1.png", categoryResponse, optionResponses),
            new ProductResponse(2L, "name2", 2000, "image2.png", categoryResponse, optionResponses),
            new ProductResponse(3L, "name3", 3000, "image3.png", categoryResponse, optionResponses));
        given(productRepository.findAll()).willReturn(productList);

        //when
        List<ProductResponse> actual = productService.getAllProducts();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("[UnitTest] 상품 추가")
    void addProduct() {
        //given
        ProductAddRequest request = ProductAddRequest.of(product);
        System.out.println("request = " + request);
        ProductResponse expected = ProductResponse.of(product);
        given(productRepository.findByContents(any(ProductAddRequest.class))).willReturn(Optional.empty());
        given(productRepository.save(any(Product.class))).willReturn(product);
        given(categoryService.findById(eq(request.categoryId()))).willReturn(category);
        given(optionService.addOptions(eq(product), eq(request.options()))).willReturn(options);

        //when
        ProductResponse actual = productService.addProduct(request);

        //then
        assertThat(actual).isEqualTo(expected);
        then(productRepository).should(times(1)).findByContents(eq(request));
        then(productRepository).should(times(1)).save(any(Product.class));
        then(categoryService).should(times(1)).findById(eq(request.categoryId()));
    }

    @Test
    @DisplayName("[UnitTest/Fail] 상품 추가: 이미 있는 상품 등록시")
    void addProduct_AlreadyExists() {
        //given
        ProductAddRequest request = new ProductAddRequest("name", 1000,"image.png", 1L, optionRequests);
        given(productRepository.findByContents(any(ProductAddRequest.class)))
            .willReturn(Optional.of(product));

        //when & then
        assertThatThrownBy(() -> productService.addProduct(request)).isInstanceOf(ProductAlreadyExistsException.class);
        then(productRepository).should(times(1)).findByContents(eq(request));
        then(productRepository).should(never()).save(any(Product.class));
    }

    @Test
    @DisplayName("[UnitTest] 상품 업데이트")
    void updateProductById() {
        //given
        Long id = 1L;
        ProductUpdateRequest request = new ProductUpdateRequest("newName", 10000, "newImage.jpg", 1L);
        given(productRepository.findById(any(Long.class))).willReturn(Optional.of(product));
        given(categoryService.findById(eq(request.categoryId()))).willReturn(category);
        ProductResponse expected = new ProductResponse(1L, "newName", 10000, "newImage.jpg", categoryResponse, optionResponses);

        //when
        ProductResponse actual = productService.updateProductById(id, request);

        //then
        assertThat(actual).isEqualTo(expected);
        then(productRepository).should(times(1)).findById(eq(id));
        then(categoryService).should(times(1)).findById(eq(request.categoryId()));
    }
}