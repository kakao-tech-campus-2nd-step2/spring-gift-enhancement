package gift.product.category.entity;

import gift.exception.category.CategoryAlreadyExistException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Categories {

    private final List<Category> categories;

    public Categories(List<Category> categories) {
        this.categories = new ArrayList<>(categories);
    }

    public void validate(Category category) {
        if (categories.stream().anyMatch(it -> it.getName().equals(category.getName()))) {
            throw new CategoryAlreadyExistException();
        }
    }

}
