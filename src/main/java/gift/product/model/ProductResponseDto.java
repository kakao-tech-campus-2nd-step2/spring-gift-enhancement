package gift.product.model;

public record ProductResponseDto(Long id, String name, Integer price, String imageUrl,
                                 Long categoryId) {

    public static ProductResponseDto from(Product product) {
        return new ProductResponseDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            product.getCategory().getId()
        );
    }
}
