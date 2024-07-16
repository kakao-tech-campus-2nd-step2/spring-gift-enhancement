package gift.product.business.dto;

import gift.product.persistence.entity.Category;
import java.util.List;

public record CategoryDto(Long id, String name) {

    public static List<CategoryDto> of(List<Category> categories) {
        return categories.stream()
            .map(category -> new CategoryDto(category.getId(), category.getName()))
            .toList();
    }
}
