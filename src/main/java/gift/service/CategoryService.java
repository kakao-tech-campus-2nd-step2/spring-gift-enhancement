package gift.service;

import gift.DTO.Category.CategoryRequest;
import gift.DTO.Category.CategoryResponse;
import gift.domain.Category;
import gift.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    @Transactional
    public void save(CategoryRequest categoryRequest){
        Category category = new Category(
                categoryRequest.getName()
        );

        categoryRepository.save(category);
    }
    public List<CategoryResponse> findAll(){
        List<CategoryResponse> answer = new ArrayList<>();

        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            answer.add(new CategoryResponse(category));
        }

        return answer;
    }
    @Transactional
    public void update(Long id, CategoryRequest category){
        Category savedCategory = categoryRepository.findById(id).orElseThrow();

        savedCategory.update(category.getName());
    }

    @Transactional
    public void delete(Long id){
        categoryRepository.deleteById(id);
    }
}
