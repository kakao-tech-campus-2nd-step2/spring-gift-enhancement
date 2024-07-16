package gift.service;

import gift.dto.category.CategoryResponse;
import gift.repository.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
            .map(category -> new CategoryResponse(
                category.getId(),
                category.getName()
            ))
            .collect(Collectors.toList());
    }
}
