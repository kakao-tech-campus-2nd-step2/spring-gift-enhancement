package gift.service;

import gift.common.dto.PageResponse;
import gift.common.exception.CategoryNotFoundException;
import gift.model.category.Category;
import gift.model.category.CategoryRequest;
import gift.model.category.CategoryResponse;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private static final Long defaultId = 1L;

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository,
        ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
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
        if (categoryId == defaultId) {
            throw new IllegalArgumentException("삭제할 수 없는 카테고리입니다.");
        }

        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException();
        }
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(CategoryNotFoundException::new);

        productRepository.updateCategory(categoryId, defaultId);
        categoryRepository.deleteById(categoryId);
    }
}
