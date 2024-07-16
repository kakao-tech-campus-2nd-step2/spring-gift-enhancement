package gift.product.controller;

import gift.product.dto.ProductDTO;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ApiProductController {

    private final ProductService productService;

    @Autowired
    public ApiProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public Page<ProductDTO> showProductList(Pageable pageable) {
        System.out.println("[ProductController] showProductList()");
        return productService.getAllProducts(pageable);
    }

    @PostMapping()
    public ResponseEntity<String> registerProduct(@Valid @RequestBody ProductDTO productDTO) {
        System.out.println("[ProductController] registerProduct()");
        productService.registerProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product registered successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        System.out.println("[ProductController] updateProduct()");
        productService.updateProduct(id, productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product update successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        System.out.println("[ProductController] deleteProduct()");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product delete successfully");
    }

    @GetMapping("/search")
    public Page<ProductDTO> searchProduct(@RequestParam("keyword") String keyword, Pageable pageable) {
        System.out.println("[ProductController] searchProduct()");
        return productService.searchProducts(keyword, pageable);
    }
}
