package gift.Service;

import gift.Model.Category;
import gift.Repository.CategoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }
}
