package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import gift.domain.category.entity.Category;
import gift.domain.category.repository.CategoryRepository;
import gift.domain.product.dto.ProductRequest;
import gift.domain.product.dto.ProductResponse;
import gift.domain.product.entity.Product;
import gift.domain.product.repository.ProductRepository;
import gift.domain.product.service.ProductService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService productService;
    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Test
    @DisplayName("Id로 Product 조회 테스트")
    void getProductTest() {
        // given
        Long id = 1L;
        Category category = new Category(1L, "test", "color", "image", "description");
        Product productList = new Product(1L, "test", 1000, "test.jpg", category);

        doReturn(Optional.of(productList)).when(productRepository).findById(id);

        ProductResponse expected = entityToDto(productList);
        // when
        ProductResponse actual = productService.getProduct(id);

        // then
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl()),
            () -> assertThat(actual.getCategoryId()).isEqualTo(expected.getCategoryId())
        );
    }

    @Test
    @DisplayName("모든 Product 조회 테스트")
    void getAllProductsTest() {
        // given
        Category category1 = new Category(1L, "test", "color", "image", "description");
        Category category2 = new Category(2L, "test", "color", "image", "description");
        Product product1 = new Product(1L, "test1", 1000, "test1.jpg", category1);
        Product product2 = new Product(2L, "test2", 2000, "test2.jpg", category2);

        List<Product> productList = Arrays.asList(product1, product2);
        Pageable pageable = PageRequest.of(0, 10);

        Page<Product> pageList = new PageImpl<>(productList, pageable, productList.size());
        Page<ProductResponse> expected = pageList.map(this::entityToDto);

        doReturn(pageList).when(productRepository).findAll(pageable);

        // when
        Page<ProductResponse> actual = productService.getAllProducts(pageable.getPageNumber(),
            pageable.getPageSize());

        // then
        assertAll(
            () -> assertThat(actual).isNotNull(),
            () -> IntStream.range(0, actual.getContent().size()).forEach(i -> {
                assertThat(actual.getContent().get(i).getName())
                    .isEqualTo(expected.getContent().get(i).getName());
                assertThat(actual.getContent().get(i).getPrice())
                    .isEqualTo(expected.getContent().get(i).getPrice());
                assertThat(actual.getContent().get(i).getImageUrl())
                    .isEqualTo(expected.getContent().get(i).getImageUrl());
                assertThat(actual.getContent().get(i).getCategoryId())
                    .isEqualTo(expected.getContent().get(i).getCategoryId());
            })
        );

    }

    @Test
    @DisplayName("product 저장 테스트")
    void createProductTest() {
        // given
        ProductRequest productRequest = new ProductRequest("test", 1000, "test.jpg", 1L);
        Category savedCategory = new Category(1L, "test", "color", "image", "description");
        Product savedProduct = new Product(productRequest.getName(), productRequest.getPrice(),
            productRequest.getImageUrl(),savedCategory);

        ProductResponse expected = entityToDto(savedProduct);

        doReturn(Optional.of(savedCategory)).when(categoryRepository).findById(any());
        doReturn(savedProduct).when(productRepository).save(any(Product.class));

        // when
        ProductResponse actual = productService.createProduct(productRequest);

        // then
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl()),
            () -> assertThat(actual.getCategoryId()).isEqualTo(expected.getCategoryId())
        );
    }

    @Test
    @DisplayName("product 업데이트 테스트")
    void updateProductTest() {
        // given
        Long id = 1L;
        Category savedCategory = new Category(1L, "test", "color", "image", "description");
        ProductRequest productRequest = new ProductRequest("update", 1000, "update.jpg", 1L);

        Product savedProduct = mock(Product.class);
        Product updatedProduct = new Product("update", 1000, "update.jpg", savedCategory);

        ProductResponse expected = entityToDto(updatedProduct);

        doReturn(Optional.of(savedCategory)).when(categoryRepository).findById(any());
        doReturn(Optional.of(savedProduct)).when(productRepository).findById(any());
        doNothing().when(savedProduct).updateAll(productRequest.getName(), productRequest.getPrice(),
            productRequest.getImageUrl(), savedCategory);
        doReturn(updatedProduct).when(productRepository).save(any(Product.class));
        // when
        ProductResponse actual = productService.updateProduct(id, productRequest);

        // then
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl()),
            () -> assertThat(actual.getCategoryId()).isEqualTo(expected.getCategoryId())
        );
    }

    @Test
    @DisplayName("product 삭제 테스트")
    void deleteProductTest0() {
        // given
        Long id = 1L;
        Category savedCategory = new Category(1L, "test", "color", "image", "description");
        Product savedProduct = new Product("test", 1000, "test.jpg", savedCategory);

        doReturn(Optional.of(savedProduct)).when(productRepository).findById(id);

        // when
        productService.deleteProduct(id);

        // then
        verify(productRepository, times(1)).delete(savedProduct);
    }

    private ProductResponse entityToDto(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl(), product.getCategory().getId());
    }
}