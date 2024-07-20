package gift.service.facade;

import gift.service.CategoryService;
import gift.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductAndCategoryFacade {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductAndCategoryFacade(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    public void deleteCategory(Long categoryId) {
        var products = productService.getProductsWithCategoryId(categoryId);
        for (var product : products) {
            productService.deleteProduct(product.id());
        }
        categoryService.deleteCategory(categoryId);
    }
}
