package gift.dto;

public record ProductResponse(
        Long id,
        String name,
        int price,
        String imageUrl,
        String categoryName,
        Long categoryId
) {
}
