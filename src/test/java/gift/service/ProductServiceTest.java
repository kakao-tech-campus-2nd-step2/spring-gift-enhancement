package gift.service;

import static org.assertj.core.api.Assertions.assertThat;

import gift.dto.ProductRequestDTO;
import gift.entity.Category;
import gift.entity.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Mock
    Pageable pageable;

    ProductRequestDTO productRequestDTO;
    ProductRequestDTO productRequestDTO2;


    @BeforeEach
    void setUp() throws Exception {
        productRepository.deleteAll();
        productRequestDTO = new ProductRequestDTO(1L, "제품", 1000, "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", "기타");
        productRequestDTO2 = new ProductRequestDTO(1L, "제품2", 1000, "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", "기타");
    }

    @AfterEach
    void tearDown() throws Exception {
        productRepository.deleteAll();
    }

    @Test
    void addProduct() {
        productService.addProduct(productRequestDTO);

        assertThat(productRepository.count()).isEqualTo(1);
    }

    @Test
    void getProductList() {
        productService.addProduct(productRequestDTO);

        assertThat(productService.getProductList(pageable).getContent().getFirst()).isNotNull();
    }

    @Test
    void updateProduct() {
        productService.addProduct(productRequestDTO);
        Product product = productRepository.findById(1L).get();
        Category category = categoryRepository.findById(1L).get();

        productService.updateProduct(1L, productRequestDTO2);

        assertThat(productRepository.findById(1L).get().getName()).isEqualTo("제품2");
    }

    @Test
    void deleteProduct() {
        productService.addProduct(productRequestDTO);
        assertThat(productRepository.count()).isEqualTo(1);
        productService.deleteProduct(1L);
        assertThat(productRepository.count()).isEqualTo(0);
    }
}