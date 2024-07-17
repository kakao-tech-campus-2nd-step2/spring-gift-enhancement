//package gift.domain;
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//class ProductTest {
//
//    @Test
//    void testProductCreation() {
//        Products product = new Products("ProductName", 1000, "http://example.com/image.jpg");
//        assertNotNull(product);
//        assertEquals("ProductName", product.getName());
//        assertEquals(1000, product.getPrice());
//        assertEquals("http://example.com/image.jpg", product.getImageUrl());
//    }
//
//    @Test
//    void testProductBuilder() {
//        Products product = new Products.Builder()
//                .name("ProductName")
//                .price(1000)
//                .imageUrl("http://example.com/image.jpg")
//                .build();
//        assertNotNull(product);
//        assertEquals("ProductName", product.getName());
//        assertEquals(1000, product.getPrice());
//        assertEquals("http://example.com/image.jpg", product.getImageUrl());
//    }
//}
