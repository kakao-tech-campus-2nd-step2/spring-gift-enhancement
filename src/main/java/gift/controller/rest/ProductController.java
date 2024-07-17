package gift.controller.rest;

import gift.entity.Product;
import gift.entity.ProductDTO;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // http://localhost:8080/api/products?page=1&size=3
    @GetMapping()
    public List<Product> getAllProducts(Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        return products.getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping()
    public ResponseEntity<Product> postProduct(@RequestBody @Valid ProductDTO form) {
        Product result = productService.save(form);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Product> putProduct(@RequestBody @Valid ProductDTO form,
                                              @PathVariable("id") Long id) {
        Product result = productService.update(id, form);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable("id") Long id) {
        productService.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Product deleted");
        return ResponseEntity.ok().body(response);
    }
}
