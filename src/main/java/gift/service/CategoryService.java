package gift.service;

import gift.domain.model.dto.CategoryResponseDto;
import gift.domain.model.entity.Category;
import gift.domain.repository.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream()
            .map(CategoryResponseDto::toDto)
            .collect(Collectors.toList());
    }
}
