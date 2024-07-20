package gift.controller.rest;

import gift.entity.Option;
import gift.entity.Product;
import gift.entity.ProductDTO;
import gift.service.ProductService;
import gift.util.ResponseUtility;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ResponseUtility responseUtility;

    @Autowired
    public ProductController(ProductService productService, ResponseUtility responseUtility) {
        this.productService = productService;
        this.responseUtility = responseUtility;
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
        return ResponseEntity
                .ok()
                .body(responseUtility.makeResponse("deleted successfully"));
    }

    // option
    @GetMapping("/{id}/options")
    public ResponseEntity<List<Option>> getProductOptions(@PathVariable("id") Long id) {
        List<Option> result = productService.getOptions(id);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/{product_id}/options/{option_id}")
    public ResponseEntity<Map<String, String>> postProductOptions(@PathVariable("product_id") Long product_id,
                                                                  @PathVariable("option_id") Long option_id) {
        productService.addProductOption(product_id, option_id);
        return ResponseEntity
                .ok()
                .body(responseUtility.makeResponse("added successfully"));
    }

    @DeleteMapping("/{product_id}/options/{option_id}")
    public ResponseEntity<Map<String, String>> deleteProductOptions(@PathVariable("product_id") Long product_id,
                                                                    @PathVariable("option_id") Long option_id) {
        productService.deleteProductOption(product_id, option_id);
        return ResponseEntity
                .ok()
                .body(responseUtility.makeResponse("deleted successfully"));
    }
}
