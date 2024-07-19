package gift.dto;

public record ProductResponse(Long id, String name, Integer price, String imageUrl,
                              ProductCategoryInformation categoryInformation) {
    public static ProductResponse of(Long id, String name, Integer price, String imageUrl, ProductCategoryInformation categoryInformation) {
        return new ProductResponse(id, name, price, imageUrl, categoryInformation);
    }
}
