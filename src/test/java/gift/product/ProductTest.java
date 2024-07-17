package gift.product;

import gift.model.product.Product;
import gift.model.product.ProductName;
import gift.repository.ProductRepository;
import gift.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ProductTest {
    private ProductService productService;
    private Product originProduct;

    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        productService = new ProductService(productRepository);
        originProduct = new Product(new ProductName("product1"),1000,"qwer.com",1000);
    }

    @Test
    public void testUpdateProduct() {
        Product newProduct = new Product(new ProductName("product2"),2000,"qwer.com",2000);
        originProduct.updateProduct(newProduct);

        assertEquals("product2", originProduct.getName().getName());
        assertEquals(2000, originProduct.getPrice());
        assertEquals("qwer.com", originProduct.getImageUrl());
        assertEquals(2000, originProduct.getAmount());
    }
}
