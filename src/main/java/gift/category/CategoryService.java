package gift.category;

import java.util.List;
import java.util.Objects;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
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
            && !Objects.equals(category.getId(),
            categoryRepository.findByName(categoryDTO.getName()).getId())) {
            throw new IllegalArgumentException("존재하는 이름입니다.");
        }
        category.update(categoryDTO.getName(), categoryDTO.getColor(), categoryDTO.getImageUrl(),
            categoryDTO.getDescription());
        categoryRepository.save(category);
    }

    public void deleteCategory(long id) throws NotFoundException {
        categoryRepository.findById(id).orElseThrow(NotFoundException::new);
        categoryRepository.deleteById(id);
    }
}
