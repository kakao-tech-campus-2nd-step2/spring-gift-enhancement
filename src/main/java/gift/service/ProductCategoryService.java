package gift.service;

import gift.dto.ProductCategoryRequest;
import gift.dto.ProductCategoryResponse;
import gift.exception.NotFoundElementException;
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

    public ProductCategoryResponse addCategory(ProductCategoryRequest productCategoryRequest) {
        var productCategory = saveCategoryWithCategoryRequest(productCategoryRequest);
        return getCategoryResponseFromCategory(productCategory);
    }

    public void updateCategory(Long id, ProductCategoryRequest productCategoryRequest) {
        var productCategory = findCategoryById(id);
        productCategory.updateCategory(productCategoryRequest.name(), productCategoryRequest.description(), productCategoryRequest.color(), productCategoryRequest.imageUrl());
        productCategoryRepository.save(productCategory);
    }

    private ProductCategory saveCategoryWithCategoryRequest(ProductCategoryRequest productCategoryRequest) {
        var productCategory = new ProductCategory(productCategoryRequest.name(), productCategoryRequest.description(), productCategoryRequest.color(), productCategoryRequest.imageUrl());
        return productCategoryRepository.save(productCategory);
    }

    private ProductCategoryResponse getCategoryResponseFromCategory(ProductCategory productCategory) {
        return ProductCategoryResponse.of(productCategory.getId(), productCategory.getName(), productCategory.getDescription(), productCategory.getColor(), productCategory.getImageUrl());
    }

    private ProductCategory findCategoryById(Long id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(id + "를 가진 상품 카테고리가 존재하지 않습니다."));
    }
}
