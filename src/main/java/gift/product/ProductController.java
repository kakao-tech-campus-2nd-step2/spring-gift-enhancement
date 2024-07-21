package gift.product;

import gift.common.model.PageResponseDto;
import gift.product.model.ProductRequest;
import gift.product.model.ProductResponse;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<ProductResponse>> getAllProducts(
        @PageableDefault(size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(
            PageResponseDto.of(productService.getAllProducts(pageable).getContent(), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(
        @Valid @RequestBody ProductRequest.Create productCreate) {
        Long productId = productService.insertProduct(productCreate);
        return ResponseEntity.created(URI.create("/api/products/" + productId)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
        @Valid @RequestBody ProductRequest.Update productUpdate,
        @PathVariable("id") Long id) {
        productService.updateProductById(id, productUpdate);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }

}
