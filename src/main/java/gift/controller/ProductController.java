package gift.controller;

import gift.dto.CategoryUpdateRequest;
import gift.dto.ProductRequest;
import gift.dto.ProductResponse;
import gift.dto.ProductUpdateRequest;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
    	this.productService = productService;
    }
    
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(@PageableDefault(sort="name") Pageable pageable) {
    	Page<ProductResponse> products = productService.getProducts(pageable);
    	return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("productId") Long productId) {
    	ProductResponse product = productService.getProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@Valid @RequestBody ProductRequest request, BindingResult bindingResult) {
        productService.createProduct(request, bindingResult);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable("productId") Long productId, 
    		@Valid @RequestBody ProductUpdateRequest request, BindingResult bindingResult) {
        productService.updateProduct(productId, request, bindingResult);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    @PutMapping("/{product_id}/category")
    public ResponseEntity<Void> updateProductCategory(@PathVariable("product_id") Long product_id,
    		@Valid @RequestBody CategoryUpdateRequest request, BindingResult bindingResult){
    	productService.updateProductCategory(product_id, request, bindingResult);
    	return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("product_id") Long product_id) {
        productService.deleteProduct(product_id);
        return ResponseEntity.status(HttpStatus.OK).build();
    } 
}
