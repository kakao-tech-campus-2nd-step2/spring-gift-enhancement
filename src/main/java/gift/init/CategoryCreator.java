package gift.init;

import gift.domain.Category.CreateCategory;
import gift.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryCreator {

    @Autowired
    private CategoryService categoryService;

    public void CategoryCreator() {
        categoryService.createCategory(new CreateCategory("Test1"));
        categoryService.createCategory(new CreateCategory("Test2"));
        categoryService.createCategory(new CreateCategory("Test3"));
    }
}
