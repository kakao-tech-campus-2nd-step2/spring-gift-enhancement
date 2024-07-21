package gift.controller;

import gift.dto.ProductRequestDTO;
import gift.dto.ProductResponseDTO;
import gift.entity.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDTO>> getProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(products);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") Long productId) {
        Product product = productService.getProduct(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(product);
    }

    @PostMapping("/products")
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        productService.saveProduct(productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Product added successfully");
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long productId) {
        System.out.println(productId);
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Product deleted successfully");
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable("productId") Long productId, @RequestBody @Valid ProductRequestDTO productRequestDTO) {
        productService.updateProduct(productId, productRequestDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Product updated successfully");
    }

}

