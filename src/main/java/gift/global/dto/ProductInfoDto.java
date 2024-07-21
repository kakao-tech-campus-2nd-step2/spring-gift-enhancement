package gift.global.dto;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public record ProductInfoDto(
    Long productId,
    String name,
    int price,
    String imageUrl,
    Long categoryId,
    String categoryName,
    String categoryImageUrl
) {

}
