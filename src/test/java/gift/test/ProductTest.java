package gift.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import gift.controller.ProductController;
import gift.entity.Category;
import gift.entity.Product;
import gift.service.ProductService;

public class ProductTest {
	
	@Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Mock
    private BindingResult bindingResult;

    private Category category;
    private Product product;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category("교환권", "#6c95d1", "https://example.com/image.jpg", "");
        product = new Product("아이스 아메리카노 T", 4500, "https://example.com/image.jpg", category);
    }

    @Test
    public void testGetAllProducts() {
    	Pageable pageable = PageRequest.of(0, 10);
    	Page<Product> productPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
    	
        when(productService.getProducts(pageable)).thenReturn(productPage);

        ResponseEntity<Page<Product>> response = productController.getAllProducts(pageable);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(productPage);
        assertThat(response.getBody().getContent()).isEmpty();
    }

    @Test
    public void testGetProduct() {
        when(productService.getProduct(1L)).thenReturn(product);

        ResponseEntity<Product> response = productController.getProduct(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(product);
    }

    @Test
    public void testAddProduct() {
    	doNothing().when(productService).createProduct(any(Product.class), any(BindingResult.class));

        ResponseEntity<Void> response = productController.addProduct(product, bindingResult);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void testUpdateProduct() {
        doNothing().when(productService).updateProduct(eq(1L), any(Product.class), any(BindingResult.class));

        ResponseEntity<Void> response = productController.updateProduct(1L, product, bindingResult);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void testDeleteProduct() {
        doNothing().when(productService).deleteProduct(1L);

        ResponseEntity<Void> response = productController.deleteProduct(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }
}
