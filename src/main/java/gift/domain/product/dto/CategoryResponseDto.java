package gift.domain.product.dto;

import gift.domain.product.entity.Category;

public record CategoryResponseDto(
    Long id,
    String name,
    String color,
    String imageUrl,
    String description
) {

    public static CategoryResponseDto from(Category category) {
        return new CategoryResponseDto(
            category.getId(),
            category.getName(),
            category.getColor(),
            category.getImageUrl(),
            category.getDescription()
        );
    }
}
