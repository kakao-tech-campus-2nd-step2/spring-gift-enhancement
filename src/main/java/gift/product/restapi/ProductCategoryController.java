package gift.product.restapi;

import gift.core.domain.product.ProductCategoryService;
import gift.product.restapi.dto.response.ProductCategoryResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    public List<ProductCategoryResponse> getCategories() {
        return productCategoryService
                .findAll()
                .stream()
                .map(ProductCategoryResponse::of)
                .toList();
    }
}
