package gift.category.application;

import gift.category.domain.Category;

public record CategoryResponse(
        Long id,
        String name,
        String color,
        String description,
        String imageUrl
) {
    public static CategoryResponse from(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getColor(),
                category.getDescription(),
                category.getImageUrl()
        );
    }
}
