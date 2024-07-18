package gift.service;

import gift.domain.Category;
import gift.repository.CategoryRepository;
import gift.web.dto.request.category.CreateCategoryRequest;
import gift.web.dto.response.category.CreateCategoryResponse;
import gift.web.dto.response.category.ReadAllCategoriesResponse;
import gift.web.dto.response.category.ReadCategoryResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;
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

    public ReadAllCategoriesResponse readAllCategories(Pageable pageable) {
        List<ReadCategoryResponse> categories = categoryRepository.findAll(pageable).stream()
            .map(ReadCategoryResponse::fromEntity)
            .toList();
        return new ReadAllCategoriesResponse(categories);
    }

    public ReadCategoryResponse readCategory(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(id + "에 해당하는 카테고리가 없습니다."));
        return ReadCategoryResponse.fromEntity(category);
    }
}
