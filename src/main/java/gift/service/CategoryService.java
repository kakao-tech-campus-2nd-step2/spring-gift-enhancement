package gift.service;

import gift.domain.Category;
import gift.dto.CategoryDto;
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

    public List<CategoryDto> getAllCategories(){
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    private CategoryDto convertToDto(Category category) {
        return new CategoryDto(
            category.getId(),
            category.getName(),
            category.getColor(),
            category.getImageUrl(),
            category.getDescription()
            );
    }
}
