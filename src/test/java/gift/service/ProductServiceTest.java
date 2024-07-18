package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import gift.dto.ProductPostRequestDTO;
import gift.entity.Category;
import gift.entity.Product;
import gift.exception.BadRequestExceptions.BadRequestException;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @MockBean
    OptionService optionService;

    Category category1;
    Product product1;
    ProductPostRequestDTO productPostRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryRepository.deleteAll();
        productRepository.deleteAll();

        category1 = categoryRepository.save(new Category("테스트1", "#000000",
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",
                ""));

        product1 = productRepository.save(new Product("커피", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",
                categoryRepository.findByName("테스트1").get()));

        productPostRequestDTO = new ProductPostRequestDTO(1L, "커피", 1234,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg",
                category1.getName(), "옵션", 1234);
    }

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void addProductAndAddOptionAtomicTest() {
        doThrow(BadRequestException.class).when(optionService).addOption(any(), any());
        assertThrows(BadRequestException.class,
                () -> productService.addProduct(productPostRequestDTO));
        assertThat(productRepository.count()).isEqualTo(1);
    }
}