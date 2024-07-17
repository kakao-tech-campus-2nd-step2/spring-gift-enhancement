package gift.util.mapper;

import gift.product.dto.category.response.CategoryResponse;
import gift.product.entity.Category;

public class CategoryMapper {

    public static CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
