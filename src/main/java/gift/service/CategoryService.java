package gift.service;

import gift.entity.Category;
import gift.entity.CategoryDTO;
import gift.entity.Product;
import gift.exception.ResourceNotFoundException;
import gift.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    Long defaultCategoryId = 1L;

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

    public Category update(Long id, CategoryDTO categoryDTO) {
        Category category = findOne(id);
        category.setName(categoryDTO.getName());
        category.setColor(categoryDTO.getColor());
        category.setImageurl(categoryDTO.getImageurl());
        category.setDescription(categoryDTO.getDescription());
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        Category category = findOne(id);
        Category defaultCategory = findOne(defaultCategoryId);

        for (Product product : category.getProducts()) {
            product.setCategory(defaultCategory);
        }

        categoryRepository.delete(category);
    }
}
