package gift.service;

import gift.exception.CustomException.CategoryNotFoundException;
import gift.exception.ErrorCode;
import gift.model.categories.Category;
import gift.model.categories.CategoryDTO;
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

    public CategoryDTO insertCategory(CategoryDTO categoryDTO) {
        return categoryRepository.save(categoryDTO.toEntity()).toDTO();
    }

    public CategoryDTO findCategoryByName(String name){
        return categoryRepository.findByName(name).orElseThrow().toDTO();
    }

    public List<CategoryDTO> getCategoryList() {
        return categoryRepository.findAll().stream().map(Category::toDTO)
            .collect(Collectors.toList());
    }

    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        return categoryRepository.save(categoryDTO.toEntity()).toDTO();
    }

    public boolean isDuplicateName(String name) {
        return categoryRepository.existsByName(name);
    }
}
