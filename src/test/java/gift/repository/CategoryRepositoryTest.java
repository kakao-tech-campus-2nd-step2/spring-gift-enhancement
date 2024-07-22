package gift.repository;

import gift.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category("Unique Category", "#000000", "http://category.jpg", "detail");
        categoryRepository.save(category);
    }

    @Test
    void testCreateAndFindCategory() {
        Category foundCategory = categoryRepository.findByName("Unique Category");
        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getName()).isEqualTo("Unique Category");
    }

    @Test
    void testDeleteCategory() {
        categoryRepository.delete(category);

        Category deletedCategory = categoryRepository.findByName("Unique Category");
        assertThat(deletedCategory).isNull();
    }

    @Test
    void testCategoryWithoutName() {
        Category categoryWithoutName = new Category(null, "#000000", "http://category.jpg", "detail");
        assertThatThrownBy(() -> categoryRepository.save(categoryWithoutName))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}