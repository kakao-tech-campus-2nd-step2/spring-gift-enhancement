package gift.dto.request;

public record AddCategoryRequest(
        String name,
        String color,
        String imageUrl,
        String description
) {
}
