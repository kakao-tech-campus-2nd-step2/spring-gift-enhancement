package gift.service;

import gift.dto.CategoryDTO;
import gift.model.Category;
import gift.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<CategoryDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(CategoryDTO::new);
    }

    public void save(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.getName(), categoryDTO.getColor(), categoryDTO.getImageUrl(), categoryDTO.getDescription());
        categoryRepository.save(category);
    }

    public void update(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        category.setName(categoryDTO.getName());
        category.setColor(categoryDTO.getColor());
        category.setImageUrl(categoryDTO.getImageUrl());
        category.setDescription(categoryDTO.getDescription());
        categoryRepository.save(category);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}