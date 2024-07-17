package gift.product.service;

import gift.product.model.Category;
import gift.product.repository.CategoryRepository;
import gift.product.validation.CategoryValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryValidation categoryValidation;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
        CategoryValidation categoryValidation) {
        this.categoryRepository = categoryRepository;
        this.categoryValidation = categoryValidation;
    }

    public void registerCategory(Category category) {
        System.out.println("[CategoryService] registerCategory()");
        categoryValidation.registerCategory(category);
        categoryRepository.save(category);
    }

    public void updateCategory(Category category) {
        System.out.println("[CategoryService] updateCategory()");
        categoryValidation.updateCategory(category);
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        System.out.println("[CategoryService] deleteCategory()");
        categoryValidation.deleteCategory(id);
        categoryRepository.deleteById(id);
    }

    public Page<Category> findAllCategory(Pageable pageable) {
        System.out.println("[CategoryService] getAllCategories()");
        return categoryRepository.findAll(pageable);
    }

    public Category findCategoryById(Long id) {
        System.out.println("[CategoryService] findCategoryById()");
        return categoryRepository.findById(id).get();
    }
}
