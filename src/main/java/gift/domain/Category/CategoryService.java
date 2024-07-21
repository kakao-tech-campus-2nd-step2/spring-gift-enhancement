package gift.domain.category;

import gift.global.exception.category.CategoryDuplicateException;
import gift.global.exception.category.CategoryNotFoundException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final gift.domain.category.JpaCategoryRepository categoryRepository;

    public CategoryService(gift.domain.category.JpaCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    public void createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new CategoryDuplicateException(categoryDTO.getName());
        }

        Category category = new Category(categoryDTO.getName(), categoryDTO.getDescription());
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new CategoryNotFoundException(id);
        }

        categoryRepository.deleteById(id);
    }

    @Transactional
    public void updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> findCategory = categoryRepository.findById(id);

        if (findCategory.isEmpty()) {
            throw new CategoryNotFoundException(id);
        }
        // 이름 중복 검사, 중복되면서 id 가 자신이 아닐 때
        Optional<Category> compareCategory = categoryRepository.findByName(categoryDTO.getName());
        if (compareCategory.isPresent() && compareCategory.get().getId() != id) {
            throw new CategoryDuplicateException(categoryDTO.getName());
        }

        findCategory.get().update(categoryDTO.getName(), categoryDTO.getDescription());
    }
}
