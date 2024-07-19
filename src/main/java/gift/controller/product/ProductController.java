package gift.controller.product;

import gift.controller.product.dto.ProductRequest;
import gift.controller.product.dto.ProductResponse;
import gift.global.auth.Authorization;
import gift.global.dto.PageResponse;
import gift.model.member.Role;
import gift.model.product.SearchType;
import gift.service.product.OptionService;
import gift.service.product.ProductService;
import gift.service.product.dto.OptionModel;
import gift.service.product.dto.ProductModel;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    private final OptionService optionService;

    public ProductController(ProductService productService, OptionService optionService) {
        this.productService = productService;
        this.optionService = optionService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse.Info> getProduct(
        @PathVariable("id") Long id
    ) {
        ProductModel.Info productModel = productService.getProduct(id);
        List<OptionModel.Info> optionModels = optionService.getOptions(id);
        ProductResponse.Info response = ProductResponse.Info.from(productModel, optionModels);
        return ResponseEntity.ok(response);
    }

    @Authorization(role = Role.ADMIN)
    @PostMapping("/products")
    public ResponseEntity<ProductResponse.Info> createProduct(
        @RequestBody @Valid ProductRequest.Register request
    ) {
        ProductModel.Info productModel = productService.createProduct(request.toProductCommand());
        List<OptionModel.Info> optionModel = optionService.createOption(productModel.id(),
            request.toOptionCommand());
        ProductResponse.Info response = ProductResponse.Info.from(productModel, optionModel);
        return ResponseEntity.ok(response);
    }


    @Authorization(role = Role.ADMIN)
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse.Info> updateProduct(
        @PathVariable("id") Long id,
        @RequestBody @Valid ProductRequest.Update request
    ) {
        ProductModel.Info productModel = productService.updateProduct(id,
            request.toProductCommand());
        List<OptionModel.Info> optionModel = optionService.getOptions(id);
        ProductResponse.Info response = ProductResponse.Info.from(productModel, optionModel);
        return ResponseEntity.ok(response);
    }

    @Authorization(role = Role.ADMIN)
    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(
        @PathVariable("id") Long id
    ) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully.");
    }

    @GetMapping("/products")
    public ResponseEntity<PageResponse<ProductResponse.Summary>> getProductsPaging(
        @RequestParam(name = "SearchType", required = false, defaultValue = "ALL") SearchType searchType,
        @RequestParam(name = "SearchValue", required = false, defaultValue = "") String searchValue,
        @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ProductModel.InfoWithOption> page = productService.getProductsPaging(searchType,
            searchValue,
            pageable);
        var response = PageResponse.from(page, ProductResponse.Summary::from);
        return ResponseEntity.ok(response);
    }

}
