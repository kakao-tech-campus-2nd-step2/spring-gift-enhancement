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

    public Category createCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.getName())) {
            throw new DuplicateCategoryNameException("중복된 카테고리 이름입니다.");
        }
        Category category = new Category(categoryRequest.getName());
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, CategoryRequest categoryRequest) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("존재하지않는 카테코리입니다."));
        if (categoryRepository.existsByName(categoryRequest.getName())) {
            throw new DuplicateCategoryNameException("중복된 카테고리 이름입니다.");
        }
        existingCategory.setName(categoryRequest.getName());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("존재하지않는 카테코리입니다."));
        categoryRepository.delete(category);
    }

}

