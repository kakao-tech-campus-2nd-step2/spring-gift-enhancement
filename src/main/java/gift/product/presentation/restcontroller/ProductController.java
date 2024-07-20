package gift.product.presentation.restcontroller;

import gift.product.presentation.dto.ProductRequest;
import gift.product.presentation.dto.ProductResponse;
import gift.product.presentation.dto.RequestOptionCreateDto;
import gift.product.business.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse.WithOptions> getProduct(@PathVariable("id") Long id) {
        var productOutWithOptions = productService.getProduct(id);
        var productResponseInfo = ProductResponse.WithOptions.from(productOutWithOptions);
        return ResponseEntity.ok(productResponseInfo);
    }

    @GetMapping
    public ResponseEntity<ProductResponse.PagingInfo> getProductsByPage(
        @PageableDefault(size = 20, sort = "modifiedDate", direction = Sort.Direction.DESC) Pageable pageable,
        @RequestParam(name = "size", required = false) Integer size) {
        if (size != null) {
            if (size < 1 || size > 100) {
                throw new IllegalArgumentException("size는 1~100 사이의 값이어야 합니다.");
            }
            pageable = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());
        }
        var productOutPaging = productService.getProductsByPage(pageable);
        var productResponsePagingInfo = ProductResponse.PagingInfo.from(productOutPaging);
        return ResponseEntity.ok(productResponsePagingInfo);
    }

    @PostMapping
    public ResponseEntity<Long> createProduct(
        @RequestBody @Valid ProductRequest.Create productRequest) {
        var productInCreate = productRequest.toProductInCreate();
        Long createdId = productService.createProduct(productInCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateProduct(
        @RequestBody @Valid ProductRequest.Update productRequest, @PathVariable("id") Long id) {
        var productInUpdate = productRequest.toProductInUpdate();
        Long updatedId = productService.updateProduct(productInUpdate, id);
        return ResponseEntity.ok(updatedId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable("id") Long id) {
        Long deletedId = productService.deleteProduct(id);
        return ResponseEntity.ok(deletedId);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProducts(@RequestBody @Valid ProductRequest.Ids ids) {
        productService.deleteProducts(ids.productIds());
        return ResponseEntity.ok().build();
    }

}
