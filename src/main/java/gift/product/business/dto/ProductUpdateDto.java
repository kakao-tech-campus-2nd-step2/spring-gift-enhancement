package gift.product.business.dto;

public record ProductUpdateDto(
    String name,
    Integer price,
    String description,
    String imageUrl,
    Long categoryId
) {

}
