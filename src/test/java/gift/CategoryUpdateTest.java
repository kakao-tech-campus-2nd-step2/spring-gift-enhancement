package gift;

import static org.junit.jupiter.api.Assertions.*;

import gift.model.Category;
import org.junit.jupiter.api.Test;

public class CategoryUpdateTest {

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