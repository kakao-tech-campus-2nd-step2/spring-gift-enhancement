package gift.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import gift.repository.CategoryRepository;
import gift.dto.CategoryDto;
import gift.dto.response.CategoryResponse;
import gift.entity.Category;
import gift.exception.CustomException;

import java.util.List;

@Service
public class CategoryService {
    
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){

        this.categoryRepository = categoryRepository;

        //TEST
        Category testCategory = new Category("test", "color", "imageUrl", "");
        Category testCategory2 = new Category("test2", "color", "imageUrl", "");
        categoryRepository.save(testCategory);
        categoryRepository.save(testCategory2);
    }

    public CategoryResponse findAll(){

        List<Category> categories = categoryRepository.findAll();
        CategoryResponse categoryResponse = new CategoryResponse(
            categories
            .stream()
            .map(CategoryDto::fromEntity)
            .toList());

        return categoryResponse;
    }

    public void addCategory(CategoryDto categoryDto){
        Category category = categoryRepository.findByName(categoryDto.getName())
                            .orElseThrow(() -> new CustomException("Category with name" + categoryDto.getName() + "exists" , HttpStatus.CONFLICT));
        
        categoryRepository.save(category);
    }

    
}
