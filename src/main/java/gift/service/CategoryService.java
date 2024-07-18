package gift.service;

import gift.domain.Category;
import gift.repository.CategoryRepository;
import gift.web.dto.request.category.CreateCategoryRequest;
import gift.web.dto.response.category.CreateCategoryResponse;
import gift.web.dto.response.category.ReadAllCategoriesResponse;
import gift.web.dto.response.category.ReadCategoryResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CreateCategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = categoryRepository.save(request.toEntity());
        return CreateCategoryResponse.fromEntity(category);
    }

    public ReadAllCategoriesResponse readAllCategories() {
        List<ReadCategoryResponse> categories = categoryRepository.findAll().stream()
            .map(ReadCategoryResponse::fromEntity)
            .toList();
        return new ReadAllCategoriesResponse(categories);
    }
}
