package gift.category;

import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
            .map(CategoryDTO::fromCategory)
            .toList();
    }

    public CategoryDTO getCategoryById(long id) throws NotFoundException {
        Category category = categoryRepository.findById(id).orElseThrow(NotFoundException::new);
        return CategoryDTO.fromCategory(category);
    }

    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    public void addCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new IllegalArgumentException("존재하는 이름입니다.");
        }
        categoryRepository.save(categoryDTO.toCategory());
    }

    public void updateCategory(CategoryDTO categoryDTO) throws NotFoundException {
        Category category = categoryRepository.findById(categoryDTO.getId())
            .orElseThrow(NotFoundException::new);
        if (categoryRepository.existsByName(categoryDTO.getName())
            && category.getId() != categoryDTO.getId()) {
            throw new IllegalArgumentException("존재하는 이름입니다.");
        }
        category.update(categoryDTO.getName());
        categoryRepository.save(category);
    }

    public void existsByNamePutResult(String name, BindingResult result) {
        if (existsByName(name)) {
            result.addError(new FieldError("categoryDTO", "name", "존재하는 이름입니다."));
        }
    }

    public void deleteCategory(long id) throws NotFoundException {
        categoryRepository.findById(id).orElseThrow(NotFoundException::new);
        categoryRepository.deleteById(id);
    }
}
