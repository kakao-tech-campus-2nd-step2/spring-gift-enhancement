package gift.product.dto;

public record ProductResponseDto(
    long productId,
    String name,
    int price,
    String imageUrl,
    long categoryId,
    String categoryName,
    String categoryImageUrl
) {

}
