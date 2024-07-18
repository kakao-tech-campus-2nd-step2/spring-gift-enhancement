package gift.util.mapper;

import gift.product.category.dto.response.CategoryResponse;
import gift.product.category.entity.Category;

public class CategoryMapper {

    public static CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
