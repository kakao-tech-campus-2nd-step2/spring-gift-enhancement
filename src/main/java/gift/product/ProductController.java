package gift.product;

import gift.product.model.ProductRequestDto;
import gift.product.model.ProductResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(
        @RequestParam(required = false, defaultValue = "0", value = "pageNo") int pageNo,
        @RequestParam(required = false, defaultValue = "10", value = "pageSize") int pageSize,
        @RequestParam(required = false, defaultValue = "id", value = "criteria") String criteria) {
        return ResponseEntity.ok()
            .body(productService.getAllProducts(pageNo, pageSize, criteria).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
            .body(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(
        @Valid @RequestBody ProductRequestDto productRequestDto) {
        productService.insertProduct(productRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
        @Valid @RequestBody ProductRequestDto productRequestDto,
        @PathVariable("id") Long id) {
        productService.updateProductById(id, productRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }

}
