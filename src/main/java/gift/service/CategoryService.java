package gift.service;

import org.springframework.stereotype.Service;

import gift.repository.CategoryRepository;
import gift.dto.CategoryDto;
import gift.entity.Category;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    
    private CategoryRepository categoryRepository;

    public CategoryService(){}

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getCategories(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                         .map(CategoryDto::fromEntity)
                         .collect(Collectors.toList());
    }
}
