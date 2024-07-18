package gift.service;

import static gift.util.constants.CategoryConstants.CATEGORY_NOT_FOUND;

import gift.dto.category.CategoryRequest;
import gift.dto.category.CategoryResponse;
import gift.exception.category.CategoryNotFoundException;
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

    // 모든 카테고리 조회
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
            .map(CategoryService::convertToDTO)
            .collect(Collectors.toList());
    }

    // ID로 카테고리 조회
    public CategoryResponse getCategoryById(Long id) {
        return categoryRepository.findById(id)
            .map(CategoryService::convertToDTO)
            .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND + id));
    }

    // 카테고리 추가
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        Category category = convertToEntity(categoryRequest);
        Category addedCategory = categoryRepository.save(category);
        return convertToDTO(addedCategory);
    }

    // 카테고리 수정
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND + id));

        category.update(categoryRequest.name(), categoryRequest.color(), categoryRequest.imageUrl(),
            categoryRequest.description());
        Category updatedCategory = categoryRepository.save(category);
        return convertToDTO(updatedCategory);
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

    private static Category convertToEntity(CategoryRequest categoryRequest) {
        return new Category(
            categoryRequest.name(),
            categoryRequest.color(),
            categoryRequest.imageUrl(),
            categoryRequest.description()
        );
    }
}
