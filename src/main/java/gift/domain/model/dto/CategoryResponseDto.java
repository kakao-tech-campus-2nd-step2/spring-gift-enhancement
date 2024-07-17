package gift.domain.model.dto;

import gift.domain.model.entity.Category;

public class CategoryResponseDto {

    private final String name;

    public CategoryResponseDto(String name) {
        this.name = name;
    }

    public static CategoryResponseDto toDto(Category category) {
        return new CategoryResponseDto(category.getName());
    }

    public String getName() {
        return name;
    }
}
