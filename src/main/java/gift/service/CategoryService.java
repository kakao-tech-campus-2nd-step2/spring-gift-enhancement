package gift.service;

import gift.common.dto.PageResponse;
import gift.common.exception.CategoryNotFoundException;
import gift.common.exception.ProductNotFoundException;
import gift.model.category.Category;
import gift.model.category.CategoryRequest;
import gift.model.category.CategoryResponse;
import gift.model.product.Product;
import gift.model.product.ProductRequest;
import gift.model.product.ProductResponse;
import gift.repository.CategoryRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public CategoryResponse register(CategoryRequest categoryRequest) {
        Category category = categoryRepository.save(categoryRequest.toEntity());
        return CategoryResponse.from(category);
    }

    public PageResponse<CategoryResponse> findAllCategory(Pageable pageable) {
        Page<Category> categoryList = categoryRepository.findAll(pageable);
        List<CategoryResponse> responses = categoryList.getContent().stream()
            .map(CategoryResponse::from).toList();
        return PageResponse.from(responses, categoryList);
    }

    public CategoryResponse findCategory(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(CategoryNotFoundException::new);
        return CategoryResponse.from(category);
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(CategoryNotFoundException::new);
        category.updateCategory(categoryRequest);
        return CategoryResponse.from(category);
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(CategoryNotFoundException::new);

        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException();
        }
        categoryRepository.deleteById(categoryId);
    }
}
