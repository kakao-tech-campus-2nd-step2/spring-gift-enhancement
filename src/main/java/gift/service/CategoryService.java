package gift.service;

import gift.dto.category.response.CategoryResponse;
import gift.dto.category.request.CreateCategoryRequest;
import gift.dto.category.request.UpdateCategoryRequest;
import gift.entity.Category;
import gift.exception.category.CategoryAlreadyExistException;
import gift.exception.category.CategoryNotFoundException;
import gift.repository.CategoryRepository;
import gift.util.mapper.CategoryMapper;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
            .map(CategoryMapper::toResponse)
            .toList();
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

    @Transactional
    public void deleteCategory(Long id) {
        validateCategory(id);
        categoryRepository.deleteById(id);
    }

    // for update
    @Transactional(readOnly = true)
    protected void validateCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new CategoryAlreadyExistException();
        }
    }

    // for delete
    @Transactional(readOnly = true)
    protected void validateCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException();
        }
    }
}
