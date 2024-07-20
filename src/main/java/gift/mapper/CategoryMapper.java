package gift.mapper;

import gift.domain.category.Category;
import gift.web.dto.CategoryDto;

public class CategoryMapper {
    public CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(),
            category.getName(),
            category.getColor(),
            category.getImageUrl(),
            category.getDescription());
    }
}