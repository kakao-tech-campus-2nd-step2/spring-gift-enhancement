package gift.product.service;

import gift.product.dto.ProductDTO;
import gift.product.exception.InvalidIdException;
import gift.product.model.Category;
import gift.product.model.Product;
import gift.product.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static gift.product.exception.GlobalExceptionHandler.NOT_EXIST_ID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<String> registerCategory(String name) {
        System.out.println("[CategoryService] registerCategory()");
        try {
            categoryRepository.save(new Category(name));
        } catch (Exception e) {
            System.out.println("[CategoryService] registerCategory(): " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Category registered successfully");
    }
}
