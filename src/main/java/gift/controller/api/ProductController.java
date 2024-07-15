package gift.controller.api;

import gift.dto.request.AddProductRequest;
import gift.dto.request.UpdateProductRequest;
import gift.dto.response.AddedProductIdResponse;
import gift.dto.response.ProductResponse;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("api/products")
    public ResponseEntity<AddedProductIdResponse> addProduct(@Valid @RequestBody AddProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.addProduct(request));
    }

    @GetMapping("api/products")
    public ResponseEntity<Page<ProductResponse>> getProducts(@PageableDefault(sort = "id") Pageable pageable) {
        Page<ProductResponse> productResponses = productService.getProductResponses(pageable);
        return ResponseEntity.ok(productResponses);
    }

    @PutMapping("api/products")
    public ResponseEntity<Void> updateProduct(@Valid @RequestBody UpdateProductRequest request) {
        productService.updateProduct(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("api/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

}
