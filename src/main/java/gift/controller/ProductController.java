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

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") long id) {
    	ProductResponse product = productService.getProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@Valid @RequestBody ProductRequest request, BindingResult bindingResult) {
        productService.createProduct(request, bindingResult);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable("id") long id, 
    		@Valid @RequestBody ProductUpdateRequest request, BindingResult bindingResult) {
        productService.updateProduct(id, request, bindingResult);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    @PutMapping("/{id}/category")
    public ResponseEntity<Void> updateProductCategory(@PathVariable("id") long id,
    		@Valid @RequestBody CategoryUpdateRequest request, BindingResult bindingResult){
    	productService.updateProductCategory(id, request, bindingResult);
    	return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    } 
}
