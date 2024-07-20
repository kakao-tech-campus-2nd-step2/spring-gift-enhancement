package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.common.dto.PageResponse;
import gift.model.product.CreateProductRequest;
import gift.model.product.ProductResponse;
import gift.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

/*@SpringBootTest
@Sql("/truncate.sql")
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("상품 등록")
    void save() {
        CreateProductRequest createProductRequest = new CreateProductRequest("product1", 1000, "image1.jpg", 1L);
        ProductResponse response = productService.addProduct(createProductRequest);

        assertAll(
            () -> assertThat(response.id()).isNotNull(),
            () -> assertThat(response.name()).isEqualTo(createProductRequest.name()),
            () -> assertThat(response.price()).isEqualTo(createProductRequest.price()),
            () -> assertThat(response.imageUrl()).isEqualTo(createProductRequest.imageUrl())
        );
    }

    @Test
    @DisplayName("상품 조회")
    void findById() {
        CreateProductRequest createProductRequest = new CreateProductRequest("product1", 1000, "image1.jpg", 1L);
        ProductResponse response = productService.addProduct(createProductRequest);
        ProductResponse product = productService.findProduct(response.id());

        assertAll(
            () -> assertThat(product.id()).isNotNull(),
            () -> assertThat(product.name()).isEqualTo(createProductRequest.name()),
            () -> assertThat(product.price()).isEqualTo(createProductRequest.price()),
            () -> assertThat(product.imageUrl()).isEqualTo(createProductRequest.imageUrl())
        );
    }

    @Test
    @DisplayName("전체 상품 조회")
    void findAll() {
        CreateProductRequest createProductRequest1 = new CreateProductRequest("product1", 1000, "image1.jpg", 1L);
        CreateProductRequest createProductRequest2 = new CreateProductRequest("product2", 2000, "image2.jpg", 1L);
        productService.addProduct(createProductRequest1);
        productService.addProduct(createProductRequest2);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        PageResponse<ProductResponse> products = productService.findAllProduct(pageable);

        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("상품 수정")
    void update() {
        CreateProductRequest createProductRequest = new CreateProductRequest("product1", 1000, "image1.jpg", 1L);
        productService.addProduct(createProductRequest);
        CreateProductRequest updateRequest = new CreateProductRequest("update1", 2000, "update1.jpg", 1L);
        ProductResponse response = productService.addProduct(createProductRequest);

        ProductResponse product = productService.updateProduct(response.id(), updateRequest);

        assertAll(
            () -> assertThat(product.name()).isEqualTo("update1"),
            () -> assertThat(product.price()).isEqualTo(2000),
            () -> assertThat(product.imageUrl()).isEqualTo("update1.jpg")
        );
    }

    @Test
    @DisplayName("상품 삭제")
    void delete() {
        CreateProductRequest createProductRequest1 = new CreateProductRequest("product1", 1000, "image1.jpg", 1L);
        CreateProductRequest createProductRequest2 = new CreateProductRequest("product2", 2000, "image2.jpg", 1L);
        productService.addProduct(createProductRequest1);
        productService.addProduct(createProductRequest2);

        productService.deleteProduct(1L);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        PageResponse<ProductResponse> products = productService.findAllProduct(pageable);

        assertThat(products.size()).isEqualTo(1);
    }
}*/
