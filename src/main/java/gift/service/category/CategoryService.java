package gift.service.category;

import gift.dto.category.CategoryRequest;
import gift.dto.category.CategoryResponse;
import gift.model.category.Category;
import gift.repository.category.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void addCategory(CategoryRequest categoryRequest) {
        Category category = categoryRequest.toEntity();
        categoryRepository.save(category);
    }

    public CategoryResponse getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("해당 카테고리가 존재하지 않습니다. id :  " + categoryId));
        return CategoryResponse.fromEntity(category);

    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryResponse::fromEntity)
                .toList();
    }

    @Transactional
    public void updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("해당 카테고리가 존재하지 않습니다. id :  " + categoryId));
        category.modify(
                categoryRequest.getName(),
                categoryRequest.getColor(),
                categoryRequest.getImageUrl(),
                categoryRequest.getDescription());
        categoryRepository.save(category);

    }


    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
