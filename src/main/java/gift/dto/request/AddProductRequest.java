package gift.dto.request;

public record AddProductRequest(
        String name,
        Integer price,
        String imageUrl,
        Long categoryId
) {
}
