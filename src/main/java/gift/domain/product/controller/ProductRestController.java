package gift.domain.product.controller;

import gift.domain.product.dto.ProductResponse;
import gift.domain.product.dto.ProductRequest;
import gift.domain.product.dto.ProductReadAllResponse;
import gift.domain.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest productRequest) {
        ProductResponse productResponse = productService.create(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @GetMapping
    public ResponseEntity<Page<ProductReadAllResponse>> readAll(Pageable pageable) {
        Page<ProductReadAllResponse> productResponseDtos = productService.readAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDtos);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> readById(@PathVariable("productId") long productId) {
        ProductResponse productResponse = productService.readById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> update(@PathVariable("productId") long productId, @RequestBody @Valid ProductRequest productRequest) {
        ProductResponse productResponse = productService.update(productId, productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable("productId") long productId) {
        productService.delete(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
