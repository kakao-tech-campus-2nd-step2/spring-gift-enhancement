package gift.service;

import gift.entity.Category;
import gift.entity.CategoryDTO;
import gift.exception.ResourceNotFoundException;
import gift.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category save(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO);
        return categoryRepository.save(category);
    }

    public Category findOne(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    public Category update(CategoryDTO categoryDTO) {
        Category category = categoryRepository.findByName(categoryDTO.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with name: " + categoryDTO.getName()));
        category.setColor(categoryDTO.getColor());
        category.setImageurl(categoryDTO.getImageurl());
        category.setDescription(categoryDTO.getDescription());
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        Category category = findOne(id);
        categoryRepository.delete(category);
    }
}
