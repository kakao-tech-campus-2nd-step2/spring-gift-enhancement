package gift.product;

import gift.common.model.PageResponseDto;
import gift.product.model.ProductRequestDto;
import gift.product.model.ProductResponseDto;
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
    public ResponseEntity<PageResponseDto<ProductResponseDto>> getAllProducts(
        @PageableDefault(size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(
            PageResponseDto.of(productService.getAllProducts(pageable).getContent(), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(
        @Valid @RequestBody ProductRequestDto productRequestDto) {
        Long productId = productService.insertProduct(productRequestDto);
        return ResponseEntity.created(URI.create("/api/products/" + productId)).build();
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
