package gift.controller.product;

import gift.controller.product.dto.ProductRequest;
import gift.controller.product.dto.ProductResponse;
import gift.global.auth.Authorization;
import gift.global.dto.PageResponse;
import gift.model.member.Role;
import gift.model.product.SearchType;
import gift.service.product.ProductService;
import gift.service.product.dto.ProductModel;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse.Info> getProduct(
        @PathVariable("id") Long id
    ) {
        var model = productService.getProduct(id);
        ProductResponse.Info response = ProductResponse.Info.from(model);
        return ResponseEntity.ok().body(response);
    }

    @Authorization(role = Role.ADMIN)
    @PostMapping("/products")
    public ResponseEntity<ProductResponse.Info> createProduct(
        @RequestBody @Valid ProductRequest.Register request
    ) {
        var model = productService.createProduct(request.toCommand());
        ProductResponse.Info response = ProductResponse.Info.from(model);
        return ResponseEntity.ok().body(response);
    }


    @Authorization(role = Role.ADMIN)
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse.Info> updateProduct(
        @PathVariable("id") Long id,
        @RequestBody @Valid ProductRequest.Update request
    ) {
        var model = productService.updateProduct(id, request.toCommand());
        ProductResponse.Info response = ProductResponse.Info.from(model);
        return ResponseEntity.ok().body(response);
    }

    @Authorization(role = Role.ADMIN)
    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(
        @PathVariable("id") Long id
    ) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().body("Product deleted successfully.");
    }

    @GetMapping("/products")
    public ResponseEntity<PageResponse<ProductResponse.Info>> getProductsPaging(
        @RequestParam(name = "SearchType", required = false, defaultValue = "ALL") SearchType searchType,
        @RequestParam(name = "SearchValue", required = false, defaultValue = "") String searchValue,
        @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ProductModel.Info> page = productService.getProductsPaging(searchType, searchValue,
            pageable);
        var response = PageResponse.from(page, ProductResponse.Info::from);
        return ResponseEntity.ok().body(response);
    }

}
