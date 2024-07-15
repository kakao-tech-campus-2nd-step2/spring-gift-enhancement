package gift.service;

import gift.exception.ErrorCode;
import gift.exception.RepositoryException;
import gift.model.Category;
import gift.model.CategoryDTO;
import gift.model.CategoryPageDTO;
import gift.repository.CategoryRepository;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.id(), categoryDTO.name(), categoryDTO.color(),
            categoryDTO.imageUrl(), categoryDTO.description());
        return convertToDTO(categoryRepository.save(category));
    }

    public CategoryPageDTO findCategoryPage(long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<CategoryDTO> categories = categoryRepository.findById(categoryId, pageable)
            .map(this::convertToDTO)
            .stream()
            .toList();
        return new CategoryPageDTO(page, size, categories.size(), categories);
    }

    public CategoryPageDTO findAllCategoryPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<CategoryDTO> categories = categoryRepository.findAll(pageable)
            .map(this::convertToDTO)
            .stream()
            .toList();
        return new CategoryPageDTO(page, size, categories.size(), categories);
    }

    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        Category currentCategory = categoryRepository.findById(categoryDTO.id()).orElseThrow(
            () -> new RepositoryException(ErrorCode.CATEGORY_NOT_FOUND, categoryDTO.id()));
        Category updateCategory = new Category(categoryDTO.id(), categoryDTO.name(),
            categoryDTO.color(), categoryDTO.imageUrl(), categoryDTO.description());
        return convertToDTO(categoryRepository.save(updateCategory));
    }

    public void deleteCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RepositoryException(ErrorCode.CATEGORY_NOT_FOUND, categoryId));
        categoryRepository.deleteById(categoryId);
    }

    private CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getColor(),
            category.getImageUrl(), category.getDescription());
    }

}
