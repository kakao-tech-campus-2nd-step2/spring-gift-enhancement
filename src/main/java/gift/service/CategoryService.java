package gift.service;

import gift.exception.NotFoundCategoryException;
import gift.model.Category;
import gift.repository.CategoryRepository;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
        Category category = new Category(name);
        categoryRepository.save(category);
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
