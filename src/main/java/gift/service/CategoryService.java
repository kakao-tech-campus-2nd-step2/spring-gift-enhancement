package gift.service;

import gift.dto.category.CategoryResponse;
import gift.entity.Category;
import gift.repository.CategoryRepository;
import gift.util.mapper.CategoryMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryPage.map(CategoryMapper::toResponse);
    }
}
