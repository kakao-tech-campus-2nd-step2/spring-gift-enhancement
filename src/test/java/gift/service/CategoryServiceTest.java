package gift.service;

import gift.entity.Category;
import gift.entity.CategoryDTO;
import gift.entity.Product;
import gift.entity.ProductDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CategoryServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @Test
    void createCategory() {
        // given
        Category category = categoryService.save(new CategoryDTO("test", "#test", "", ""));

        // when
        // then
        assertThat(category.getId()).isNotNull();
    }

    @Test
    void readCategory() {
        // given
        Category category = categoryService.save(new CategoryDTO("test", "#test", "", ""));

        // when
        Category expect = categoryService.findById(category.getId());

        // then
        assertThat(expect.getId()).isEqualTo(category.getId());
    }

    @Test
    void updateCategory() {
        // given
        Category category = categoryService.save(new CategoryDTO("test1", "#test1", "1", "1"));

        // when
        CategoryDTO categoryDTO = new CategoryDTO("test2", "#test2", "2", "2");
        Category expect = categoryService.update(category.getId(), categoryDTO);

        // then
        assertThat(expect.getName()).isEqualTo(categoryDTO.getName());
        assertThat(expect.getColor()).isEqualTo(categoryDTO.getColor());
        assertThat(expect.getImageurl()).isEqualTo(categoryDTO.getImageurl());
        assertThat(expect.getDescription()).isEqualTo(categoryDTO.getDescription());
    }


    @Test
    void deleteCategory() {
        // given
        Category category = categoryService.save(new CategoryDTO("test", "#test", "", ""));

        // when
        categoryService.delete(category.getId());

        // then
        Category expect = categoryService.findById(category.getId());
        assertThat(expect.getName()).isEqualTo("DefaultCategory");
    }

    @Test
    @DisplayName("상품에 매핑된 카테고리가 삭제될 시 해당 상품의 category가 [DefaultCategory]로 리턴됨")
    void deleteCategoryTest() {
        // given
        Category category = categoryService.save(new CategoryDTO("test", "#test", "test.com", ""));
        Product product = productService.save(new ProductDTO("test", 123, "test.com", category.getId()));

        // when
        categoryService.delete(category.getId());
        em.flush();
        em.clear();

        // then
        Product expectProduct = productService.findById(product.getId());
        Category expectCategory = categoryService.findById(expectProduct.getCategoryid());
        assertThat(expectCategory.getId()).isEqualTo(1L); // defaultCategory
    }
}
