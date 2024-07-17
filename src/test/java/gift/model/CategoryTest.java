package gift.model;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void testCreateValidCategory() {
        Category category = new Category(1L, "교환권");
        assertAll(
            () -> assertThat(category.getId()).isNotNull(),
            () -> assertThat(category.getName()).isEqualTo("교환권")
        );
    }

    @Test
    void testCreateWithNullName() {
        try {
            Category category = new Category(1L, null);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithEmptyName() {
        try {
            Category category = new Category(1L, "");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

}