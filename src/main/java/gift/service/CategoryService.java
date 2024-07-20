package gift.service;

import gift.dto.CategoryResponseDto;
import gift.entity.Category;
import gift.entity.Product;
import gift.repository.CategoryRepositoryInterface;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepositoryInterface categoryRepositoryInterface;
    private ProductService productService;

    public CategoryService(CategoryRepositoryInterface categoryRepositoryInterface) {
        this.categoryRepositoryInterface = categoryRepositoryInterface;
    }

    public List<Category> getAllCategories() {
        return categoryRepositoryInterface.findAll();
    }

    public CategoryResponseDto getDtoOfAllCategories() {
       return new CategoryResponseDto(getAllCategories());
    }

    public Category getCategoryByName(String categoryName) {
        return categoryRepositoryInterface.findCategoryByName(categoryName);
    }

    public CategoryResponseDto getCategoryDtoByProductId(Long productId) {
        Product product = productService.findById(productId);
        Long categoryId= product.getCategory().getId();
        Category category= categoryRepositoryInterface.getById(categoryId);
        return CategoryResponseDto.fromEntity(category);
    }
}
