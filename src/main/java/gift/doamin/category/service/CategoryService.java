package gift.doamin.category.service;

import gift.doamin.category.entity.Category;
import gift.doamin.category.repository.JpaCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final JpaCategoryRepository categoryRepository;

    public CategoryService(JpaCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void CreateCategory(String name) {
        categoryRepository.save(new Category(name));
    }
}
