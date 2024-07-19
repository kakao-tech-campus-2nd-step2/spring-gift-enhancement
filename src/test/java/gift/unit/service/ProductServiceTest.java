package gift.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

import gift.exception.product.ProductNotFoundException;
import gift.product.dto.request.CreateProductRequest;
import gift.product.dto.request.UpdateProductRequest;
import gift.product.dto.response.ProductResponse;
import gift.product.entity.Category;
import gift.product.entity.Product;
import gift.product.repository.CategoryRepository;
import gift.product.repository.ProductRepository;
import gift.product.service.ProductService;
import gift.util.mapper.ProductMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

class ProductServiceTest implements AutoCloseable {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Override
    public void close() throws Exception {
        closeable.close();
    }


    @Test
    @DisplayName("getAllProducts empty test")
    @Transactional
    void getAllProductsEmptyTest() {
        // given
        Pageable pageable = Pageable.unpaged();
        given(productRepository.findAll(pageable)).willReturn(Page.empty());

        // when
        Page<ProductResponse> products = productService.getAllProducts(pageable);

        // then
        assertThat(products).isEmpty();
    }

    @Test
    @DisplayName("getAllProducts test")
    @Transactional
    void getAllProductsTest() {
        // given
        Pageable pageable = Pageable.unpaged();
        Category category = new Category("Category A");
        List<Product> productList = List.of(
            Product.builder().id(1L).name("Product A").price(1000)
                .imageUrl("http://example.com/images/product_a.jpg").category(category).build(),
            Product.builder().id(2L).name("Product B").price(2000)
                .imageUrl("http://example.com/images/product_b.jpg").category(category).build(),
            Product.builder().id(3L).name("Product C").price(3000)
                .imageUrl("http://example.com/images/product_c.jpg").category(category).build(),
            Product.builder().id(4L).name("Product D").price(4000)
                .imageUrl("http://example.com/images/product_d.jpg").category(category).build(),
            Product.builder().id(5L).name("Product E").price(5000)
                .imageUrl("http://example.com/images/product_e.jpg").category(category).build()
        );
        Page<Product> productPage = new PageImpl<>(productList);
        given(productRepository.findAll(pageable)).willReturn(productPage);

        // when
        List<ProductResponse> products = productService.getAllProducts(pageable).toList();

        // then
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(5);
        then(productRepository).should(times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("getProductById exception test")
    @Transactional
    void getProductByIdExceptionTest() {
        // given
        given(productRepository.findById(7L)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> productService.getProductById(7L))
            .isInstanceOf(ProductNotFoundException.class);
        then(productRepository).should(times(1)).findById(7L);
    }

    @Test
    @DisplayName("getProductById test")
    @Transactional
    void getProductByIdTest() {
        // given
        Product expected = Product.builder()
            .id(2L)
            .name("Product B")
            .price(2000)
            .imageUrl("http://example.com/images/product_b.jpg")
            .build();

        given(productRepository.findById(2L)).willReturn(Optional.of(expected));

        // when
        Product actual = productRepository.findById(2L).get();

        // then
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
        assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl());
        then(productRepository).should(times(1)).findById(2L);
    }

    @Test
    @DisplayName("Add product test")
    void createProductTest() {
        // given
        CreateProductRequest request = new CreateProductRequest("Product A", 1000,
            "http://example.com/images/product_a.jpg", 1L);
        Category category = new Category(1L, "Category A");
        Product savedProduct = Product.builder()
            .id(1L)
            .name("Product A")
            .price(1000)
            .imageUrl("http://example.com/images/product_a.jpg")
            .category(category)
            .build();
        given(productRepository.save(any(Product.class))).willReturn(savedProduct);
        given(categoryRepository.findById(any(Long.class))).willReturn(Optional.of(category));

        // when
        Long savedId = productService.createProduct(request);

        // then
        assertThat(savedId).isNotNull();
        then(productRepository).should(times(1)).save(any(Product.class));
        then(categoryRepository).should(times(1)).findById(any(Long.class));
    }

    @Test
    @DisplayName("updateProduct test")
    @Transactional
    void updateProductTest() {
        // given
        Category category = new Category(1L, "Category A");
        Product product = Product.builder()
            .id(1L)
            .name("Product A")
            .price(1000)
            .imageUrl("http://example.com/images/product_a.jpg")
            .build();
        UpdateProductRequest request = new UpdateProductRequest("product3", 30000, null, 1L);
        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(categoryRepository.findById(any(Long.class))).willReturn(Optional.of(category));

        // when
        productService.updateProduct(1L, request);
        Product actual = productRepository.findById(1L).get();
        ProductMapper.updateProduct(actual, request, category);
        Product expected = Product.builder()
            .id(1L)
            .name("product3")
            .price(30000)
            .category(category)
            .build();

        // then
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
        assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl());
        assertThat(actual.getCategoryName()).isEqualTo(expected.getCategoryName());
        then(productRepository).should(times(2)).findById(1L);
    }

    @Test
    @DisplayName("deleteProduct test")
    @Transactional
    void deleteProductTest() {
        // given
        given(productRepository.existsById(1L)).willReturn(true);
        willDoNothing().given(productRepository).deleteById(1L);

        // when
        productService.deleteProduct(1L);

        // then
        then(productRepository).should(times(1)).existsById(1L);
        then(productRepository).should(times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteProduct exception test")
    @Transactional
    void deleteProductExceptionTest() {
        // given
        given(productRepository.existsById(9L)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> productService.deleteProduct(9L))
            .isInstanceOf(ProductNotFoundException.class);
        then(productRepository).should(times(1)).existsById(9L);
    }
}
