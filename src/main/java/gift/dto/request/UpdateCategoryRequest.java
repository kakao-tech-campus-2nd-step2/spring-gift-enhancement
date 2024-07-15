package gift.dto.request;

public record UpdateCategoryRequest(
        Long id,
        String name,
        String color,
        String imageUrl,
        String description
) {
}
