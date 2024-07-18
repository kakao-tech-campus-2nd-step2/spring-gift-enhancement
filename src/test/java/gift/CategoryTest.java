package gift;

import static org.junit.jupiter.api.Assertions.*;

import gift.model.Category;
import gift.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class CategoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void whenCategoryIsCreated_thenCategorySavedCorrectly() {
        Category category = new Category(null, "TestCategory");
        Category savedCategory = categoryRepository.save(category);

        assertAll(
            () -> assertNotNull(savedCategory.getId()),
            () -> assertEquals("TestCategory", savedCategory.getName())
        );
    }

    @Test
    public void testUpdate() {
        // given
        Category category = new Category(1L, "Original Name");

        // when
        category.update("Updated Name");

        // then
        assertAll(
            () -> assertEquals(1L, category.getId()),
            () -> assertEquals("Updated Name", category.getName())
        );
    }
}