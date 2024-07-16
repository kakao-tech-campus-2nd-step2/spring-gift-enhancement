package gift.domain.category.service;

import gift.domain.category.dto.CategoryRequest;
import gift.domain.category.dto.CategoryResponse;
import gift.domain.category.entity.Category;
import gift.domain.category.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<CategoryResponse> getAllCategories(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return categoryRepository.findAll(pageable).map(this::entityToDto);
    }

    public CategoryResponse createCategory(CategoryRequest request) {
        Category savedCategory = categoryRepository.save(dtoToEntity(request));
        return entityToDto(savedCategory);
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category savedCategory = categoryRepository.findById(id).orElseThrow();
        savedCategory.updateAll(request.getName(), request.getColor(), request.getImageUrl(),
            request.getDescription());

        Category updatedCategory = categoryRepository.save(savedCategory);

        return entityToDto(updatedCategory);
    }

    public void deleteCategory(Long id){
        Category savedCategory = categoryRepository.findById(id).orElseThrow();
        categoryRepository.delete(savedCategory);
    }

    private CategoryResponse entityToDto(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getColor(), category.getImageUrl(), category.getDescription());
    }

    private Category dtoToEntity(CategoryRequest request) {
        return new Category(request.getName(), request.getColor(), request.getImageUrl(), request.getDescription());
    }

}
