package gift.service;

import gift.dto.ProductCategoryRequest;
import gift.dto.ProductCategoryResponse;
import gift.model.MemberRole;
import gift.model.ProductCategory;
import gift.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public ProductCategoryResponse addProductCategory(ProductCategoryRequest productCategoryRequest, MemberRole memberRole) {
        var productCategory = saveProductCategoryWithProductCategoryRequest(productCategoryRequest);
        return getProductCategoryResponseFromProductCategory(productCategory);
    }

    private ProductCategory saveProductCategoryWithProductCategoryRequest(ProductCategoryRequest productCategoryRequest) {
        var productCategory = new ProductCategory(productCategoryRequest.name(), productCategoryRequest.description(), productCategoryRequest.color(), productCategoryRequest.imageUrl());
        return productCategoryRepository.save(productCategory);
    }

    private ProductCategoryResponse getProductCategoryResponseFromProductCategory(ProductCategory productCategory) {
        return ProductCategoryResponse.of(productCategory.getId(), productCategory.getName(), productCategory.getDescription(), productCategory.getColor(), productCategory.getImageUrl());
    }
}
