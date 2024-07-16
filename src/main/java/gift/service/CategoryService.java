package gift.service;

import gift.domain.Category;
import gift.domain.CategoryRequest;
import gift.domain.Menu;
import gift.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void create(CategoryRequest categoryRequest) {
        categoryRepository.save(MapCategoryRequsetToCategory(categoryRequest));
    }

    public Category MapCategoryRequsetToCategory(CategoryRequest categoryRequest){
        return new Category(categoryRequest.id(),categoryRequest.name(),new LinkedList<Menu>());
    }
}
