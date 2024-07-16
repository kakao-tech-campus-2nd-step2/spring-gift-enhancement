package gift.service;

import gift.controller.dto.request.ProductRequest;
import gift.model.Category;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("상품 업데이트 테스트[성공]")
    void updateProduct() {
        // given
        String name = "카테고리";
        String name2 = "카테고리2";
        String color = "#123456";
        String imageUrl = "이미지url";
        String description = "설명";
        Category category = categoryRepository.save(new Category(name, color, imageUrl, description));
        Category category2 = categoryRepository.save(new Category(name2, color, imageUrl, description));
        Product saved = productRepository.save(new Product("pname", 1000, "purl", category));
        ProductRequest request = new ProductRequest(saved.getName(), saved.getPrice(), saved.getImageUrl(), category2.getName());

        // when
        productService.updateById(saved.getId(), request);
        Product actual = productRepository.findByIdFetchJoin(saved.getId()).get();

        // then
        assertThat(actual.getCategory().getName()).isEqualTo(name2);
    }
}