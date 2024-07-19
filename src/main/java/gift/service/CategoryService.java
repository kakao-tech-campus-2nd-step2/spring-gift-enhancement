package gift.service;

import gift.DTO.CategoryDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gift.mapper.CategoryMapper;
import gift.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        return categoryMapper.toCategoryDTO(categoryRepository.save(categoryMapper.toCategoryEntity(categoryDTO)));
    }

    public List<CategoryDTO> getAllCategories() {
        var categories = categoryRepository.findAll();
        return categories.stream()
            .map(categoryMapper::toCategoryDTO)
            .collect(Collectors.toList());
    }
}
