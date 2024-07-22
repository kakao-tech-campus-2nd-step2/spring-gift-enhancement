package gift.controller;

import gift.auth.CheckRole;
import gift.request.ProductAddRequest;
import gift.response.ProductOptionsResponse;
import gift.response.ProductResponse;
import gift.request.ProductUpdateRequest;
import gift.exception.InputException;
import gift.model.Product;
import gift.service.OptionsService;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductApiController {

    private final ProductService productService;
    private final OptionsService optionsService;

    public ProductApiController(ProductService productService, OptionsService optionsService) {
        this.productService = productService;
        this.optionsService = optionsService;
    }

    @CheckRole("ROLE_ADMIN")
    @GetMapping("/api/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> dtoList;
        List<Product> productsList = productService.getAllProducts();

        dtoList = productsList.stream()
            .map(ProductResponse::new)
            .toList();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @CheckRole("ROLE_ADMIN")
    @GetMapping("/api/products/{id}/all")
    public ResponseEntity<ProductOptionsResponse> getProductWithAllOptions(
        @PathVariable("id") Long id) {
        Product product = productService.getProduct(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        ProductOptionsResponse dto = optionsService.getAllProductOptions(product);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @CheckRole("ROLE_ADMIN")
    @GetMapping("/api/products/{id}")
    public ResponseEntity<ProductOptionsResponse> getProductWithOption(
        @PathVariable("id") Long id, @RequestParam("option_id") Long optionId) {
        Product product = productService.getProduct(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        ProductOptionsResponse dto = optionsService.getProductOption(product, optionId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @CheckRole("ROLE_ADMIN")
    @PostMapping("/api/products")
    public ResponseEntity<Void> addProduct(@RequestBody @Valid ProductAddRequest dto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult.getAllErrors());
        }

        Product savedProduct = productService.addProduct(dto.name(), dto.price(), dto.imageUrl(),
            dto.categoryName());
        optionsService.addOption(dto.optionName(), dto.quantity(), savedProduct.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CheckRole("ROLE_ADMIN")
    @PutMapping("/api/products")
    public ResponseEntity<Void> updateProduct(@RequestBody @Valid ProductUpdateRequest dto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult.getAllErrors());
        }

        productService.updateProduct(dto.id(), dto.name(), dto.price(), dto.imageUrl(),
            dto.categoryName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @CheckRole("ROLE_ADMIN")
    @DeleteMapping("/api/products")
    public ResponseEntity<Void> deleteProduct(@RequestParam("id") Long id) {
        optionsService.deleteAllOptions(id);
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
