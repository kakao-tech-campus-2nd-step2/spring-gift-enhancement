package gift.service;

import gift.entity.Category;
import gift.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found"));
        existingCategory.setName(category.getName());
        existingCategory.setColor(category.getColor());
        existingCategory.setImageUrl(category.getImageUrl());
        existingCategory.setDescription(category.getDescription());
        return categoryRepository.save(existingCategory);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
