package gift;

import static org.junit.jupiter.api.Assertions.*;

import gift.model.Name;
import gift.model.Product;
import org.junit.jupiter.api.Test;

public class ProductUpdateTest {

    @Test
    public void testUpdate() {
        // given
        Name originalName = new Name("Original Name");
        Name updatedName = new Name("Updated Name");
        Product product = new Product(1L, originalName, 100, "http://original.image.url", 1L);

        // when
        product.update(updatedName, 200, "http://updated.image.url", 2L);

        // then
        assertAll(
            () -> assertEquals(1L, product.getId()),
            () -> assertEquals(updatedName, product.getName()),
            () -> assertEquals(200, product.getPrice()),
            () -> assertEquals("http://updated.image.url", product.getImageUrl()),
            () -> assertEquals(2L, product.getCategoryId())
        );
    }
}