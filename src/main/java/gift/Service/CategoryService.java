package gift.Service;

import gift.Model.Category;
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

    public Category getCategoryById(Long id){
        return categoryRepository.findCategoryById(id);
    }

    public Category addCategory(Category category){
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category){
        return categoryRepository.save(category);
    }

    public Category deleteCategory(Long id){
        productRepository.deleteByCategoryId(id);
        Category deleteCategories = categoryRepository.findCategoryById(id);
        categoryRepository.deleteById(id);
        return deleteCategories;
    }
}
