package gift.dto;

import gift.vo.Product;

public record ProductResponseDto (
        Long id,
        CategoryDto categoryDto,
        String name,
        Integer price,
        String imageUrl
) {
    public static ProductResponseDto toProductResponseDto (Product product) {
        return new ProductResponseDto(
            product.getId(),
            CategoryDto.fromCategory(product.getCategory()),
            product.getName(),
            product.getPrice(),
            product.getImageUrl()
        );
    }
}
