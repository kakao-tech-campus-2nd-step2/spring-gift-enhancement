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

    public void registerCategory(String name) {
        System.out.println("[CategoryService] registerCategory()");
        categoryRepository.save(new Category(name));
    }

    public void updateCategory(Category category) {
        System.out.println("[CategoryService] updateCategory()");
        categoryRepository.save(category);
    }
}
