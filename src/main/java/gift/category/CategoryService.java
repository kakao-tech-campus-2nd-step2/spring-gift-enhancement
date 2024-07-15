package gift.category;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDTO insertNewCategory(CategoryRequestDTO newCategory) {
        Category category = new Category(newCategory.getName(), newCategory.getColor(), newCategory.getImageUrl(), newCategory.getDescription());
        return new CategoryResponseDTO(categoryRepository.save(category));
    }

    public List<CategoryResponseDTO> findAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryResponseDTO::new).toList();
    }

    public CategoryResponseDTO findCategoriesByID(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        return new CategoryResponseDTO(category);
    }

    @Transactional
    public CategoryResponseDTO updateCategoriesByID(Long id, CategoryRequestDTO updateParam) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(updateParam.getName());
        category.setColor(updateParam.getColor());
        category.setImageUrl(updateParam.getImageUrl());
        category.setDescription(updateParam.getDescription());
        return new CategoryResponseDTO(category);
    }

    public void deleteCategoriesByID(Long id) {
        categoryRepository.deleteById(id);
    }
}
