package gift.service;

import gift.domain.Category;
import gift.dto.request.CategoryRequest;
import gift.exception.CategoryNotFoundException;
import gift.exception.DuplicateCategoryNameException;
import gift.repository.category.CategorySpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static gift.exception.ErrorCode.CATEGORY_NOT_FOUND;
import static gift.exception.ErrorCode.DUPLICATE_CATEGORY_NAME;

@Service
@Transactional
public class CategoryService {

    private final CategorySpringDataJpaRepository categoryRepository;

    @Autowired
    public CategoryService(CategorySpringDataJpaRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));
    }

    public Category createCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.getName())) {
            throw new DuplicateCategoryNameException(DUPLICATE_CATEGORY_NAME);
        }
        Category category = new Category(categoryRequest.getName());
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, CategoryRequest categoryRequest) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));
        if (categoryRepository.existsByName(categoryRequest.getName())) {
            throw new DuplicateCategoryNameException(DUPLICATE_CATEGORY_NAME);
        }
        existingCategory.setName(categoryRequest.getName());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));
        categoryRepository.delete(category);
    }

}

