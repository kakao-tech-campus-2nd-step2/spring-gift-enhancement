package gift.service;

import gift.dto.category.CategoryResponse;
import gift.dto.category.CreateCategoryRequest;
import gift.dto.category.UpdateCategoryRequest;
import gift.entity.Category;
import gift.exception.category.CategoryAlreadyExistException;
import gift.exception.category.CategoryNotFoundException;
import gift.repository.CategoryRepository;
import gift.util.mapper.CategoryMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryPage.map(CategoryMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategory(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(CategoryNotFoundException::new);
        return CategoryMapper.toResponse(category);
    }

    @Transactional
    public Long createCategory(CreateCategoryRequest request) {
        Category newCategory = new Category(request.name());
        validateCategory(newCategory);
        Category savedCategory = categoryRepository.save(newCategory);
        return savedCategory.getId();
    }

    @Transactional
    public void updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(CategoryNotFoundException::new);
        category.setName(request.name());
    }

    @Transactional(readOnly = true)
    protected void validateCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new CategoryAlreadyExistException();
        }
    }
}
