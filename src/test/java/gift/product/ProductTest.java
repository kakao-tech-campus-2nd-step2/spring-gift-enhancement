package gift.product;

import gift.model.product.Product;
import gift.model.product.ProductName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ProductTest {
    private Product originProduct;

    @BeforeEach
    public void setUp() {
        originProduct = new Product(new ProductName("product1"),1000,"qwer.com",1000);
    }

    @Test
    public void updateProductTest() {
        Product newProduct = new Product(new ProductName("product2"),2000,"qwer.com",2000);
        originProduct.updateProduct(newProduct);

        assertEquals("product2", originProduct.getName().getName());
        assertEquals(2000, originProduct.getPrice());
        assertEquals("qwer.com", originProduct.getImageUrl());
        assertEquals(2000, originProduct.getAmount());
    }
}
