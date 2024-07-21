package gift.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OptionTest {

    private Option option;
    private Category category;
    private Product product;

    @BeforeEach
    void setUp() {
        category = new Category(null, "교환권");
        product = new Product(1L, "kbm", "100", category, "https://kakao");
        option = new Option(3L, "임시옵션", 100L, product);
    }

    @Test
    void testCreateWithNullName() {
        try {
            option = new Option(null, null, 1L, product);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithEmptyName() {
        try {
            option = new Option(null, "", 1L, product);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithLengthName() {
        try {
            option = new Option(null, "abcde".repeat(300), 1L, product);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithInvalidName() {
        try {
            option = new Option(null, "<>\\.", 1L, product);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithNullQuantity() {
        try {
            option = new Option(null, "임시옵션", null, product);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWith0Quantity() {
        try {
            option = new Option(null, "임시옵션", 0L, product);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWith0LessQuantity() {
        try {
            option = new Option(null, "임시옵션", -100L, product);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWith1MillionGreaterQuantity() {
        try {
            option = new Option(null, "임시옵션", 100_000_000L, product);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateValidOption() {
        assertAll(
            () -> assertThat(option.getId()).isNotNull(),
            () -> assertThat(option.getName()).isEqualTo("임시옵션"),
            () -> assertThat(option.getQuantity()).isEqualTo(100L),
            () -> assertThat(option.getProduct().getId()).isNotNull()
        );
    }

    @Test
    void testUpdateWithValidName() {
        option.updateName("테스트");
        assertThat("테스트").isEqualTo(option.getName());
    }

    @Test
    void testUpdateWithNullName() {
        try {
            option.updateName(null);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testUpdateWithEmptyName() {
        try {
            option.updateName("");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testUpdateWithLengthName() {
        try {
            option.updateName("test".repeat(300));
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testUpdateWithInvalidName() {
        try {
            option.updateName(".<>");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testUpdateValidQuantity() {
        option.updateQuantity(200L);
        assertThat(200L).isEqualTo(option.getQuantity());
    }

    @Test
    void testUpdateWithNullQuantity() {
        try {
            option.updateQuantity(null);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testUpdateWith0Quantity() {
        try {
            option.updateQuantity(0L);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testUpdateWith0LessQuantity() {
        try {
            option.updateQuantity(-200L);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testUpdateWith1MillionGreaterQuantity() {
        try {
            option.updateQuantity(100_000_000L);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

}