package gift.product.service;

import gift.product.dto.ProductDTO;
import gift.product.exception.InvalidIdException;
import gift.product.model.Category;
import gift.product.model.Product;
import gift.product.repository.CategoryRepository;
import gift.product.validation.CategoryValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static gift.product.exception.GlobalExceptionHandler.NOT_EXIST_ID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryValidation categoryValidation;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
        CategoryValidation categoryValidation) {
        this.categoryRepository = categoryRepository;
        this.categoryValidation = categoryValidation;
    }

    public void registerCategory(Category category) {
        System.out.println("[CategoryService] registerCategory()");
        categoryValidation.registerCategory(category);
        categoryRepository.save(category);
    }

    public void updateCategory(Category category) {
        System.out.println("[CategoryService] updateCategory()");
        categoryValidation.updateCategory(category);
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        System.out.println("[CategoryService] deleteCategory()");
        categoryValidation.deleteCategory(id);
        categoryRepository.deleteById(id);
    }
}
