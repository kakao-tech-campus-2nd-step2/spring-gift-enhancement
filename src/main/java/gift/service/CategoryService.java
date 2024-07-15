package gift.service;

import gift.controller.dto.CategoryDTO;
import gift.repository.CategoryRepository;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> findAllCategory(){
        List<CategoryDTO> categories = categoryRepository.findAll().stream().map(
            category -> new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getColor(),
                category.getImageUrl(),
                category.getDescription()
            )
        ).toList();
        return categories;
    }
}
