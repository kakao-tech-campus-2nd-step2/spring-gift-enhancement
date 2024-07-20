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
    }

    @Test
    void testCreateWithNullName() {
        try{
            option = new Option(null, null, 1L, product);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithEmptyName() {
        try{
            option = new Option(null, "", 1L, product);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithLengthName() {
        try{
            option = new Option(null, "abcde".repeat(300), 1L, product);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithInvalidName() {
        try{
            option = new Option(null, "<>\\.", 1L, product);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithNullQuantity() {
        try{
            option = new Option(null, "임시옵션", null, product);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWith0Quantity() {
        try{
            option = new Option(null, "임시옵션", 0L, product);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWith0LessQuantity() {
        try{
            option = new Option(null, "임시옵션", -100L, product);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWith1MillionGreaterQuantity() {
        try{
            option = new Option(null, "임시옵션", 100_000_000L, product);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateValidOption() {
        option = new Option(3L, "임시옵션", 100L, product);
        assertAll(
            () -> assertThat(option.getId()).isNotNull(),
            () -> assertThat(option.getName()).isEqualTo("임시옵션"),
            () -> assertThat(option.getQuantity()).isEqualTo(100L),
            () -> assertThat(option.getProduct().getId()).isNotNull()
        );
    }

}