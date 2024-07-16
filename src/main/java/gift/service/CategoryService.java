package gift.service;

import gift.dto.CategoryDTO;
import gift.model.Category;
import gift.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void saveCategory(CategoryDTO categoryDTO){
        System.out.println(categoryDTO);
        Category category = new Category(categoryDTO.getName());
        categoryRepository.save(category);
    }

    public List<Category> getAllCategories(){
        System.out.println(categoryRepository.findAll());
        return categoryRepository.findAll();
    }
}
