package gift.dto;

public record ProductCategoryResponse(Long id, String name, String description, String color, String imageUrl) {
    public static ProductCategoryResponse of(Long id, String name, String description, String color, String imageUrl) {
        return new ProductCategoryResponse(id, name, description, color, imageUrl);
    }
}
