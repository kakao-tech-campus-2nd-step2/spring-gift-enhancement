package gift.service;

import gift.exception.category.DuplicateCategoryException;
import gift.exception.category.NotFoundCategoryException;
import gift.model.Category;
import gift.repository.CategoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(NotFoundCategoryException::new);
    }

    @Transactional
    public void addCategory(String name) {
        categoryRepository.findByName(name)
            .ifPresent(category -> {
                throw new DuplicateCategoryException();
            });

        categoryRepository.save(new Category(name));
    }

    @Transactional
    public void updateCategory(Long id, String name) {
        categoryRepository.findById(id)
            .ifPresentOrElse(c -> c.updateCategory(name),
                () -> {
                    throw new NotFoundCategoryException();
                });
    }

    @Transactional
    public void removeCategory(Long id) {
        categoryRepository.findById(id)
            .ifPresentOrElse(categoryRepository::delete,
                () -> {
                    throw new NotFoundCategoryException();
                });
    }

}
