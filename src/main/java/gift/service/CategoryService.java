package gift.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import gift.repository.CategoryRepository;
import gift.dto.CategoryDto;
import gift.entity.Category;
import gift.exception.CustomException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getCategories(){
        
        //TEST
        Category testCategory = new Category("test");
        Category testCategory2 = new Category("test2");
        categoryRepository.save(testCategory);
        categoryRepository.save(testCategory2);

        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                        .map(CategoryDto::fromEntity)
                        .collect(Collectors.toList());
        }

    public void addCategory(CategoryDto categoryDto){
        Category category = categoryRepository.findByName(categoryDto.getName())
                            .orElseThrow(() -> new CustomException("Category with name" + categoryDto.getName() + "exists" , HttpStatus.CONFLICT));
        
        categoryRepository.save(category);
    }

    
}
