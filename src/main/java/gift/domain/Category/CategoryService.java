package gift.domain.Category;

import gift.global.exception.BusinessException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final JpaCategoryRepository categoryRepository;

    public CategoryService(JpaCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    public void createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "동일한 이름의 카테고리 존재");
        }

        Category category = new Category(categoryDTO.getName(), categoryDTO.getDescription());
        categoryRepository.save(category);
    }
}
