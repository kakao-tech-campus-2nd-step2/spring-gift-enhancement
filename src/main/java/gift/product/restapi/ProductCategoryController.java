package gift.product.restapi;

import gift.core.PagedDto;
import gift.core.domain.product.ProductCategory;
import gift.core.domain.product.ProductCategoryService;
import gift.product.restapi.dto.response.PagedCategoryResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    public PagedCategoryResponse getCategories(
            @PageableDefault Pageable pageable
    ) {
        PagedDto<ProductCategory> categories = productCategoryService.findAll(pageable);

        return PagedCategoryResponse.from(categories);
    }
}
