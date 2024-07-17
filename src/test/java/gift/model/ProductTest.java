package gift.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void testCreateValidProduct() {
        Category category = new Category(1L, "교환권");
        Product product = new Product(1L, "상품", "100", category, "https://kakao");
        assertAll(
            () -> assertThat(product.getId()).isNotNull(),
            () -> assertThat(product.getName()).isEqualTo("상품"),
            () -> assertThat(product.getPrice()).isEqualTo("100"),
            () -> assertThat(product.getCategory().getId()).isEqualTo(1L),
            () -> assertThat(product.getImageUrl()).isEqualTo("https://kakao")
        );
    }

    @Test
    void testCreateWithNullName() {
        try {
            Category category = new Category(1L, "교환권");
            Product nullNameProduct = new Product(1L, null, "100", category, "https://kakao");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithEmptyName() {
        try {
            Category category = new Category(1L, "교환권");
            Product emptyNameProduct = new Product(1L, "", "200", category, "https://kakao");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithLengthName() {
        try {
            Category category = new Category(1L, "교환권");
            Product lengthNameProduct = new Product(1L, "aaaa aaaa aaaa a", "200", category,
                "https://kakao");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithInvalidName() {
        try {
            Category category = new Category(1L, "교환권");
            Product invalidNameProduct = new Product(1L, ".", "100", category, "https://kakao");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithKaKaoName() {
        try {
            Category category = new Category(1L, "교환권");
            Product kakaoNameProduct = new Product(1L, "카카오", "100", category, "https://kakao");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }


    @Test
    void testCreateWithNullPrice() {
        try {
            Category category = new Category(1L, "교환권");
            Product nullPriceProduct = new Product(1L, "상품", null, category, "https://kakao");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithEmptyPrice() {
        try {
            Category category = new Category(1L, "교환권");
            Product emptyPriceProduct = new Product(1L, "상품", "", category, "https://kakao");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }


    @Test
    void testCreateWithInvalidPrice() {
        try {
            Category category = new Category(1L, "교환권");
            Product invalidPriceProduct = new Product(1L, "상품", "abcde", category, "https://kakao");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithNullImageUrl() {
        try {
            Category category = new Category(1L, "교환권");
            Product nullImageUrlProduct = new Product(1L, "상품", "100", category, null);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithEmptyImageUrl() {
        try {
            Category category = new Category(1L, "교환권");
            Product emptyImageUrlProduct = new Product(1L, "상품", "100", category, "");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void testCreateWithInvalidImageUrl() {
        try {
            Category category = new Category(1L, "교환권");
            Product invalidImageUrlProduct = new Product(1L, "상품", "100", category, "kbm");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }
}