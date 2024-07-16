package gift.service;

import gift.dto.CategoryDTO;
import gift.repository.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getCategories() {
        return categoryRepository.findAll()
            .stream()
            .map(category -> category.toDTO())
            .collect(Collectors.toList());
    }
}
