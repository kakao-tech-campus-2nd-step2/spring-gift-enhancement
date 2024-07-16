package gift.service;

import gift.domain.Category;
import gift.exception.MemberNotFoundException;
import gift.repository.category.CategorySpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategorySpringDataJpaRepository categoryRepository;

    @Autowired
    public CategoryService(CategorySpringDataJpaRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category){
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category category){
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("존재하지않는 카테코리입니다."));
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}

