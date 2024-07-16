package gift.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WishTest {

    @Test
    void testWishCreation() {
        Member member = new Member("test@example.com", "password123");
        Products product = new Products("ProductName", 1000, "http://example.com/image.jpg");

        Wish wish = new Wish(member, product);
        assertNotNull(wish);
        assertEquals(member, wish.getMember());
        assertEquals(product, wish.getProduct());
    }

    @Test
    void testWishBuilder() {
        Member member = new Member("test@example.com", "password123");
        Products product = new Products("ProductName", 1000, "http://example.com/image.jpg");

        Wish wish = new Wish.Builder()
                .member(member)
                .product(product)
                .build();
        assertNotNull(wish);
        assertEquals(member, wish.getMember());
        assertEquals(product, wish.getProduct());
    }
}
