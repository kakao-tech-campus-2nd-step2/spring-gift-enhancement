package gift.util.mapper;

import gift.dto.category.CategoryResponse;
import gift.entity.Category;

public class CategoryMapper {

    public static CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
