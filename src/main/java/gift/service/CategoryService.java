package gift.service;

import gift.dto.CategoryDTO;
import gift.model.Category;
import gift.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void saveCategory(CategoryDTO categoryDTO){
        Category category = new Category(categoryDTO.getName());
        categoryRepository.save(category);
    }

    public List<Category> getAllCategories(){
        log.info(categoryRepository.findAll().toString());
        return categoryRepository.findAll();
    }
}
