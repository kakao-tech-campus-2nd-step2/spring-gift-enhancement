package gift.service;

import gift.entity.Category;
import gift.repository.CategoryRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepositoryInterface categoryRepositoryInterface;

    public CategoryService(CategoryRepositoryInterface categoryRepositoryInterface) {
        this.categoryRepositoryInterface = categoryRepositoryInterface;
    }

    public List<Category> getAllCategories() {
        return categoryRepositoryInterface.findAll();
    }

}
