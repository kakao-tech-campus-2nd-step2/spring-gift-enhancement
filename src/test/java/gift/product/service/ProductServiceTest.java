package gift.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.verify;

import gift.category.entity.Category;
import gift.category.repository.CategoryRepository;
import gift.option.dto.OptionReqDto;
import gift.option.dto.OptionResDto;
import gift.option.entity.Option;
import gift.option.service.OptionService;
import gift.product.dto.ProductReqDto;
import gift.product.dto.ProductResDto;
import gift.product.entity.Product;
import gift.product.exception.ProductCreateException;
import gift.product.exception.ProductDeleteException;
import gift.product.exception.ProductErrorCode;
import gift.product.exception.ProductNotFoundException;
import gift.product.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
@DisplayName("상품 서비스 테스트")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private OptionService optionService;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("전체 상품 조회")
    void getProducts() {
        //given
        given(productRepository.findAll(any(Pageable.class))).willReturn(
                new PageImpl<>(List.of(
                        new Product("상품1", 1000, "product1.png", mock(Category.class)),
                        new Product("상품2", 2000, "product2.png", mock(Category.class))
                ), Pageable.ofSize(2), 2)
        );

        //when
        Page<ProductResDto> productPage = productService.getProducts(Pageable.ofSize(2));
        List<ProductResDto> products = productPage.getContent();

        //then
        assertThat(productPage.getTotalElements()).isEqualTo(2);
        assertThat(productPage.getTotalPages()).isOne();

        assertThatList(products).hasSize(2);
        assertThatList(products).containsExactlyElementsOf(
                List.of(
                        new ProductResDto(new Product("상품1", 1000, "product1.png", mock(Category.class))),
                        new ProductResDto(new Product("상품2", 2000, "product2.png", mock(Category.class)))
                )
        );

        verify(productRepository).findAll(Pageable.ofSize(2));
    }

    @Test
    @DisplayName("단일 상품 조회 성공")
    void getProduct_success() {
        //given
        Product product1 = new Product("상품1", 1000, "product1.png", mock(Category.class));

        given(productRepository.findById(anyLong())).willReturn(Optional.of(product1));

        //when
        ProductResDto product = productService.getProduct(1L);

        //then
        assertThat(product).isEqualTo(new ProductResDto(product1));
        assertThat(product.name()).isEqualTo("상품1");
        assertThat(product.price()).isEqualTo(1000);
        assertThat(product.imageUrl()).isEqualTo("product1.png");

        verify(productRepository).findById(1L);
    }

    @Test
    @DisplayName("단일 상품 조회 실패")
    void getProduct_fail() {
        //given
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> productService.getProduct(1L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage(ProductErrorCode.PRODUCT_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("상품 옵션 조회 성공")
    void getProductOptions_success() {
        //given
        Product product1 = new Product("상품1", 1000, "product1.png", mock(Category.class));
        List.of(
                new Option("옵션1", 100),
                new Option("옵션2", 200)
        ).forEach(product1::addOption);

        given(productRepository.findById(anyLong())).willReturn(Optional.of(product1));

        //when
        List<OptionResDto> options = productService.getProductOptions(1L);

        //then
        assertThatList(options).hasSize(2);
        assertThatList(options).containsExactlyElementsOf(
                List.of(
                        new OptionResDto(new Option("옵션1", 100)),
                        new OptionResDto(new Option("옵션2", 200))
                )
        );
    }

    @Test
    @DisplayName("상품 옵션 조회 실패")
    void getProductOptions_fail() {
        //given
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> productService.getProductOptions(1L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage(ProductErrorCode.PRODUCT_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("상품 추가 성공")
    void addProduct_success() {
        // given
        Category category = new Category("테스트 카테고리", "#FFFFFF", "test_category.png", "테스트 카테고리 설명");
        ProductReqDto productReqDto = new ProductReqDto("테스트 상품", 1000, "test_product.png", "테스트 카테고리",
                List.of(
                        new OptionReqDto("옵션1", 100),
                        new OptionReqDto("옵션2", 200)
                )
        );

        Product savedProduct = new Product("테스트 상품", 1000, "test_product.png", category);
        savedProduct.addOption(new Option("옵션1", 100));
        savedProduct.addOption(new Option("옵션2", 200));

        given(categoryRepository.findByName("테스트 카테고리")).willReturn(Optional.of(category));
        given(productRepository.save(any(Product.class))).willReturn(savedProduct);

        // when
        ProductResDto product = productService.addProduct(productReqDto);

        // then
        assertThat(product).isNotNull();
        assertThat(product.name()).isEqualTo("테스트 상품");
        assertThat(product.price()).isEqualTo(1000);
        assertThat(product.imageUrl()).isEqualTo("test_product.png");
        assertThat(product.category()).isEqualTo("테스트 카테고리");

        verify(categoryRepository).findByName("테스트 카테고리");
        verify(productRepository).save(any(Product.class));
        verify(optionService).addOptions(any(Product.class), eq(productReqDto.options()));
    }

    @Test
    @DisplayName("상품 추가 실패")
    void addProduct_fail() {
        //given
        given(categoryRepository.findByName(any())).willReturn(Optional.empty());
        given(productRepository.save(any(Product.class))).willThrow(InvalidDataAccessApiUsageException.class);

        //then
        assertThatThrownBy(() -> productService.addProduct(new ProductReqDto("상품1", 1000, "product1.png", "카테고리", List.of())))
                .isInstanceOf(ProductCreateException.class)
                .hasMessage(ProductErrorCode.PRODUCT_CREATE_FAILED.getMessage());
    }

    @Test
    @DisplayName("상품 수정 성공")
    void updateProduct_success() {
        // given
        Long productId = 1L;

        Category oldCategory = new Category("old카테고리", "#000000", "old_category.png", "이전 카테고리 설명");
        Category newCategory = new Category("new카테고리", "#FFFFFF", "new_category.png", "이후 카테고리 설명");

        Product oldProduct = new Product("old상품", 1000, "old_product.png", oldCategory);
        List.of(
                new Option("old옵션1", 100),
                new Option("old옵션2", 200)
        ).forEach(oldProduct::addOption);

        ProductReqDto productReqDto = new ProductReqDto("new상품", 2000, "new_product.png", "new카테고리",
                List.of(
                        new OptionReqDto("new옵션1", 150),
                        new OptionReqDto("new옵션2", 250)
                )
        );

        given(productRepository.findById(productId)).willReturn(Optional.of(oldProduct));
        given(categoryRepository.findByName("new카테고리")).willReturn(Optional.of(newCategory));

        // when
        productService.updateProduct(productId, productReqDto);

        // then
        assertThat(oldProduct.getName()).isEqualTo("new상품");
        assertThat(oldProduct.getPrice()).isEqualTo(2000);
        assertThat(oldProduct.getImageUrl()).isEqualTo("new_product.png");
        assertThat(oldProduct.getCategory()).isEqualTo(newCategory);

        verify(categoryRepository).findByName(eq("new카테고리"));
        verify(optionService).updateOptions(eq(oldProduct), eq(productReqDto.options()));
    }

    @Test
    @DisplayName("상품 수정 실패")
    void updateProduct_fail() {
        //given
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> productService.updateProduct(1L, new ProductReqDto("상품1", 1000, "product1.png", "카테고리", List.of())))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage(ProductErrorCode.PRODUCT_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("상품 삭제 성공")
    void deleteProduct_success() {
        //given
        Long productId = 1L;
        Product product = new Product("삭제될 상품", 1000, "delete_product.png", mock(Category.class));

        given(productRepository.findById(productId)).willReturn(Optional.of(product));

        //when
        productService.deleteProduct(productId);

        //then
        verify(productRepository).findById(productId);
        verify(productRepository).delete(product);
    }

    @Test
    @DisplayName("상품 삭제 실패 - 상품 없음")
    void deleteProduct_fail_productNotFound() {
        //given
        Long productId = 1L;
        given(productRepository.findById(productId)).willReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> productService.deleteProduct(productId))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage(ProductErrorCode.PRODUCT_NOT_FOUND.getMessage());
    }
    
    @Test
    @DisplayName("상품 삭제 실패 - 상품 삭제 중 에러 발생")
    void deleteProduct_fail_deleteException() {
        //given
        Long productId = 1L;
        Product product = new Product("삭제될 상품", 1000, "delete_product.png", mock(Category.class));

        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        willThrow(MockitoException.class).given(productRepository).delete(product);    // delete는 void 메서드이므로 예외를 발생시키기 위해 willThrow를 사용
        
        //then
        assertThatThrownBy(() -> productService.deleteProduct(productId))
                .isInstanceOf(ProductDeleteException.class)
                .hasMessage(ProductErrorCode.PRODUCT_DELETE_FAILED.getMessage());
    }
}
