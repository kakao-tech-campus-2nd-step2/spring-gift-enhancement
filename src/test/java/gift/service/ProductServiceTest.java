package gift.service;

import gift.domain.Category;
import gift.domain.Option;
import gift.domain.Product;
import gift.dto.request.OptionRequest;
import gift.dto.request.ProductRequest;
import gift.dto.request.UpdateProductRequest;
import gift.exception.CustomException;
import gift.repository.CategoryRepository;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static gift.constant.Message.*;
import static gift.exception.ErrorCode.DUPLICATE_OPTION_NAME_ERROR;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ProductServiceTest {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private OptionRepository optionRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        optionRepository = mock(OptionRepository.class);
        productService = new ProductService(productRepository, categoryRepository, optionRepository);
    }

    @Test
    void addProduct() {
        //given
        ProductRequest productRequest = new ProductRequest("newProduct", 500, "image.image", "뷰티");
        OptionRequest optionRequest = new OptionRequest("newOption", 5);

        Category category = new Category(1L, "뷰티");
        Option option = new Option(optionRequest);

        given(categoryRepository.findByName(any())).willReturn(Optional.of(new Category()));
        given(productRepository.save(any())).willReturn(new Product(productRequest, category, option));
        given(optionRepository.save(any())).willReturn(new Option(optionRequest));

        // when
        String successMsg = productService.addProduct(productRequest, optionRequest);

        // then
        Assertions.assertThat(successMsg).isEqualTo(ADD_SUCCESS_MSG);
    }

    @Test
    void updateProductCategory() {
        // given
        Product product = new Product(1L, "name", 500, "image.image");
        Category category1 = new Category(1L, "상품권");
        product.setCategory(category1);

        Category category2 = new Category(2L, "뷰티");
        Long requestId = 1L;
        UpdateProductRequest updateProductRequest = new UpdateProductRequest("name", 500, "image.image", "뷰티");

        given(productRepository.findProductById(any())).willReturn(Optional.of(new Product()));
        given(categoryRepository.findByName(any())).willReturn(Optional.of(new Category()));

        // when
        String successMsg = productService.updateProduct(requestId, updateProductRequest);

        // then
        Assertions.assertThat(successMsg).isEqualTo(UPDATE_SUCCESS_MSG);
    }

    @Test
    void addProductOption() {
        // given
        Product product = new Product(1L, "name", 500, "image.image");
        Category category1 = new Category(1L, "상품권");
        Option option = new Option("optionName", 100, product);
        product.setCategory(category1);
        product.setOption(option);

        Long requestId = 1L;
        OptionRequest optionRequest = new OptionRequest("optionName", 5);

        given(productRepository.findProductById(any())).willReturn(Optional.of(new Product()));
        given(optionRepository.findAllByProduct(any())).willReturn(List.of(option));
        given(optionRepository.save(any())).willReturn(new Option(optionRequest));

        // when
        // then
        assertThrows(CustomException.class, () -> {
            productService.addOption(requestId, optionRequest);
        });
    }
}