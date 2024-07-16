package gift.service;

import static gift.util.Constants.CATEGORY_NOT_FOUND;

import gift.dto.category.CategoryResponse;
import gift.exception.product.ProductNotFoundException;
import gift.model.Category;
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

    // 모든 상품 조회
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
            .map(CategoryService::convertToDTO)
            .collect(Collectors.toList());
    }

    // ID로 상품 조회
    public CategoryResponse getCategoryById(Long id) {
        return categoryRepository.findById(id)
            .map(CategoryService::convertToDTO)
            .orElseThrow(() -> new ProductNotFoundException(CATEGORY_NOT_FOUND + id));
    }

    // Mapper methods
    private static CategoryResponse convertToDTO(Category category) {
        return new CategoryResponse(
            category.getId(),
            category.getName(),
            category.getColor(),
            category.getImageUrl(),
            category.getDescription()
        );
    }
}
