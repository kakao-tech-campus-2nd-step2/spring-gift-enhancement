package gift.service;

import gift.converter.CategoryConverter;
import gift.dto.CategoryDTO;
import gift.dto.PageRequestDTO;
import gift.model.Category;
import gift.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<CategoryDTO> findAllCategories(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.toPageRequest();
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(CategoryConverter::convertToDTO);
    }

    public Long addCategory(CategoryDTO categoryDTO) {
        Category category = CategoryConverter.convertToEntity(categoryDTO);
        categoryRepository.save(category);
        return category.getId();
    }

    public Optional<CategoryDTO> findCategoryById(Long id) {
        return categoryRepository.findById(id)
            .map(CategoryConverter::convertToDTO);
    }

    @Transactional
    public void updateCategory(CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(categoryDTO.getId())
            .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        existingCategory = new Category(existingCategory.getId(), categoryDTO.getName());
        categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}