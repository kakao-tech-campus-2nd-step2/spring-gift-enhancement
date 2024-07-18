package gift.domain.product.dto;

import gift.domain.product.entity.Product;

public record ProductResponseDto(
    Long id,
    CategoryResponseDto category,
    String name,
    int price,
    String imageUrl
) {
    public static ProductResponseDto from(Product product) {
        return new ProductResponseDto(
            product.getId(),
            CategoryResponseDto.from(product.getCategory()),
            product.getName(),
            product.getPrice(),
            product.getImageUrl()
        );
    }
}
