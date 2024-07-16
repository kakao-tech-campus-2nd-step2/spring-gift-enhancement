package gift.service;

import gift.entity.Category;
import gift.entity.CategoryDTO;
import gift.entity.Product;
import gift.entity.ProductDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CategoryServiceSpringBootTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("상품에 매핑된 카테고리가 삭제될 시 해당 상품의 category가 [DefaultCategory]로 변함")
    void deleteCategoryTest() {
        // given
        Category category = categoryService.save(new CategoryDTO("test", "#test", "test.com", ""));
        Product product = productService.save(new ProductDTO("test", 123, "test.com", category.getId()));

        // when
        categoryService.delete(category.getId());
        em.flush();
        em.clear();

        // then
        Product result = productService.findById(product.getId());
        Assertions.assertThat(result.getCategory().getName()).isEqualTo("DefaultCategory");
    }
}
