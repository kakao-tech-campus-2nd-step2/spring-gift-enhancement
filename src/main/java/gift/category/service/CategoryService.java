package gift.category.service;

import gift.category.domain.Category;
import gift.category.domain.CategoryDTO;
import gift.category.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.text.html.Option;
import org.hibernate.query.Page;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
            .map(category -> new CategoryDTO(category.getId(), category.getName()))
            .collect(Collectors.toList());
    }

    public Optional<CategoryDTO> findById(Long id) {
        return categoryRepository.findById(id)
            .map(category -> new CategoryDTO(category.getId(), category.getName()));
    }

    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.name());
        categoryRepository.save(category);
        return categoryDTO;
    }

    public CategoryDTO update(CategoryDTO categoryDTO) {
        if (categoryRepository.existsById(categoryDTO.id())) {
            Category category = categoryRepository.findById(categoryDTO.id()).get();
            category.setName(categoryDTO.name());
            categoryRepository.save(category);
            return new CategoryDTO(category.getId(), category.getName());
        }
        throw new EntityNotFoundException("Category not found with id: " + categoryDTO.id());
    }
}
