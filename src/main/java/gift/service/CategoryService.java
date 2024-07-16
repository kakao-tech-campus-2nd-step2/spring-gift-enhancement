package gift.service;

import gift.domain.Category;
import gift.dto.request.CategoryRequest;
import gift.exception.MemberNotFoundException;
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

    public Category createCategory(CategoryRequest categoryRequest){
        Category category = new Category(categoryRequest.getName());
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, CategoryRequest categoryRequest){
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("존재하지않는 카테코리입니다."));
        existingCategory.setName(categoryRequest.getName());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}

