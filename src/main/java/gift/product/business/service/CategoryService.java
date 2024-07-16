package gift.product.business.service;

import gift.product.business.dto.CategoryDto;
import gift.product.persistence.repository.CategoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getCategories() {
        var categories = categoryRepository.getAllCategories();
        return CategoryDto.of(categories);
    }
}
