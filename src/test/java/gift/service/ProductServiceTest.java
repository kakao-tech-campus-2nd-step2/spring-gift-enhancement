package gift.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;

import gift.exception.category.NotFoundCategoryException;
import gift.model.Category;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository = mock(ProductRepository.class);
    private CategoryRepository categoryRepository = mock(CategoryRepository.class);

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, categoryRepository);
    }

    @DisplayName("상품+카테고리 저장 테스트")
    @Test
    void save() {
        //given
        Category category = new Category("카테고리");
        given(categoryRepository.findByName(any(String.class)))
            .will(invocationOnMock -> {
                String name = invocationOnMock.getArgument(0, String.class);
                return Optional.of(new Category(name));
            });
        given(productRepository.save(any(Product.class))).
            willReturn(new Product(1L, "name", 1000, "http://a.com", category));
        //when
        productService.addProduct("name", 1000, "http://a.com", category.getName());

        //then
        then(categoryRepository).should().findByName(any(String.class));
        then(productRepository).should().save(any(Product.class));
    }

    @DisplayName("상품+존재하지 않는 카테고리 저장 테스트")
    @Test
    void failSave() {
        //given
        Category category = new Category("카테고리");
        given(categoryRepository.findByName(category.getName()))
            .willThrow(NotFoundCategoryException.class);
        given(productRepository.save(any(Product.class))).
            willReturn(new Product(1L, "name", 1000, "http://a.com", category));
        //when //then
        assertThatThrownBy(
            () -> productService.addProduct("name", 1000, "http://", category.getName()))
            .isInstanceOf(NotFoundCategoryException.class);
    }

    @DisplayName("상품+카테고리 변경 테스트")
    @Test
    void update() {
        //given
        Category oldCategory = new Category("카테고리");
        Category newCategory = new Category("새로운 카테고리");
        Product product = new Product(1L, "name", 1000, "http://a.com", oldCategory);

        given(categoryRepository.findByName(any(String.class)))
            .will(invocationOnMock -> {
                String name = invocationOnMock.getArgument(0, String.class);
                return Optional.of(new Category(name));
            });
        given(productRepository.findById(any(Long.class)))
            .willReturn(Optional.of(product));

        //when
        productService.editProduct(product.getId(), product.getName(), 1000, "http://a.com",
            newCategory.getName());
        //then
        then(categoryRepository).should().findByName(any(String.class));
        then(productRepository).should().findById(any(Long.class));
    }

    @DisplayName("상품+카테고리 변경 실패 테스트")
    @Test
    void failUpdate() {
        //given
        Category oldCategory = new Category("카테고리");
        Category newCategory = new Category("변경된 카테고리");
        Product product = new Product(1L, "name", 1000, "http://a.com", oldCategory);

        given(categoryRepository.findByName(newCategory.getName()))
            .willReturn(Optional.empty());
        //when //then
        assertThatThrownBy(() -> productService.editProduct(product.getId(), product.getName(),
            product.getPrice(), product.getImageUrl(), newCategory.getName()))
            .isInstanceOf(NotFoundCategoryException.class);
    }


}