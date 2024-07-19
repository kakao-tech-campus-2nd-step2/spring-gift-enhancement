package gift.domain.product.controller;

import gift.domain.option.dto.OptionRequest;
import gift.domain.option.dto.OptionResponse;
import gift.domain.option.service.OptionService;
import gift.domain.product.dto.ProductCreateResponse;
import gift.domain.product.dto.ProductRequest;
import gift.domain.product.dto.ProductResponse;
import gift.domain.product.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
    private final OptionService optionService;

    public ProductController(ProductService productService, OptionService optionService) {
        this.productService = productService;
        this.optionService = optionService;
    }

    @GetMapping()
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
        @RequestParam(defaultValue = "0") int pageNo,
        @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<ProductResponse> response = productService.getAllProducts(pageNo, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Long id) {
        ProductResponse response = productService.getProduct(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/options")
    public ResponseEntity<List<OptionResponse>> getProductOptions(@PathVariable("id") Long id) {
        List<OptionResponse> response = optionService.getProductOptions(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<ProductCreateResponse> createProduct(
        @RequestBody @Valid ProductRequest productRequest) {
        ProductCreateResponse response = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
    }

    @PostMapping("/{id}/options")
    public ResponseEntity<OptionResponse> addOptionToProduct(@PathVariable("id") Long id,
        @Valid @RequestBody OptionRequest request) {
        OptionResponse response = optionService.addOptionToProduct(id, request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Long id,
        @RequestBody @Valid ProductRequest productRequest) {
        ProductResponse response = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
