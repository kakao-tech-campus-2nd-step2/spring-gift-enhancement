package gift.api.product;

import gift.api.option.OptionService;
import gift.api.option.dto.OptionRequest;
import gift.api.option.dto.OptionResponse;
import gift.api.product.dto.ProductRequest;
import gift.api.product.dto.ProductResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.data.domain.Pageable;
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
    private final OptionService optionService;

    public ProductController(ProductService productService, OptionService optionService) {
        this.productService = productService;
        this.optionService = optionService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> getProducts(Pageable pageable) {
        return ResponseEntity.ok().body(productService.getProducts(pageable));
    }

    @PostMapping()
    public ResponseEntity<Void> add(@Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.created(
            URI.create("/api/products/" + productService.add(productRequest))).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @Valid @RequestBody ProductRequest productRequest) {
        productService.update(id, productRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/options")
    public ResponseEntity<List<OptionResponse>> getOptions(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(optionService.getOptions(id));
    }

    @PostMapping("/{id}/options")
    public ResponseEntity<Void> addOption(@PathVariable("id") Long id,
                                    @RequestBody OptionRequest optionRequest) {
        optionService.add(id, optionRequest);
        return ResponseEntity.created(URI.create("/api/products/" + id)).build();
    }
}
