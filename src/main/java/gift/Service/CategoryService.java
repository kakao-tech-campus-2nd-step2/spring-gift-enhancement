package gift.Service;

import gift.Model.Category;
import gift.Model.Product;
import gift.Repository.CategoryRepository;
import gift.Repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository){
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    public Category addCategory(Category category){
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Long id){
        return categoryRepository.findCategoryById(id);
    }

    public Category updateCategory(Category category){
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id){
        productRepository.changeCategoryNull(id);
        categoryRepository.deleteById(id);
    }
}
