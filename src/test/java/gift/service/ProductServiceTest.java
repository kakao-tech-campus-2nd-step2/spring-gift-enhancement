package gift.service;

import gift.domain.Category;
import gift.domain.Product;
import gift.dto.request.AddProductRequest;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static gift.constant.Message.ADD_SUCCESS_MSG;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ProductServiceTest {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        productService = new ProductService(productRepository, categoryRepository);
    }

    @Test
    void addProduct() {
        //given
        AddProductRequest addProductRequest = new AddProductRequest("newProduct", 500, "image.image", "뷰티");
        Category category = new Category(1L, "뷰티");
        given(categoryRepository.findByName(any())).willReturn(Optional.of(new Category()));
        given(productRepository.save(any())).willReturn(new Product(addProductRequest, category));

        // when
        String successMsg = productService.addProduct(addProductRequest);

        // then
        Assertions.assertThat(successMsg).isEqualTo(ADD_SUCCESS_MSG);
    }

    @Test
    void updateProduct() {
    }
}