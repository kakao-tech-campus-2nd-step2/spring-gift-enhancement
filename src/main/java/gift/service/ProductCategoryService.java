package gift.service;

import gift.dto.ProductCategoryRequest;
import gift.dto.ProductCategoryResponse;
import gift.exception.DuplicatedNameException;
import gift.exception.NotFoundElementException;
import gift.model.ProductCategory;
import gift.repository.ProductCategoryRepository;
import gift.repository.ProductRepository;
import gift.repository.WishProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final WishProductRepository wishProductRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository, ProductRepository productRepository, WishProductRepository wishProductRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
        this.wishProductRepository = wishProductRepository;
    }

    public ProductCategoryResponse addCategory(ProductCategoryRequest productCategoryRequest) {
        categoryNameValidation(productCategoryRequest.name());
        var productCategory = saveCategoryWithCategoryRequest(productCategoryRequest);
        return getCategoryResponseFromCategory(productCategory);
    }

    public void updateCategory(Long id, ProductCategoryRequest productCategoryRequest) {
        categoryNameValidation(productCategoryRequest.name());
        var productCategory = findCategoryById(id);
        productCategory.updateCategory(productCategoryRequest.name(), productCategoryRequest.description(), productCategoryRequest.color(), productCategoryRequest.imageUrl());
        productCategoryRepository.save(productCategory);
    }

    @Transactional(readOnly = true)
    public ProductCategoryResponse getCategory(Long id) {
        var productCategory = findCategoryById(id);
        return getCategoryResponseFromCategory(productCategory);
    }

    @Transactional(readOnly = true)
    public List<ProductCategoryResponse> getCategories(Pageable pageable) {
        return productCategoryRepository.findAll(pageable)
                .stream()
                .map(this::getCategoryResponseFromCategory)
                .toList();
    }

    public void deleteCategory(Long id) {
        var productList = productRepository.findAllByProductCategoryId(id);
        for (var product : productList) {
            wishProductRepository.deleteAllByProductId(product.getId());
        }
        productRepository.deleteAllByProductCategoryId(id);
        productCategoryRepository.deleteById(id);
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

    private void categoryNameValidation(String name) {
        if (productCategoryRepository.existsByName(name)) {
            throw new DuplicatedNameException("이미 존재하는 카테고리 이름입니다.");
        }
    }
}
