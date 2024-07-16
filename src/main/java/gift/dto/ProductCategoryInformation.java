package gift.dto;

public record ProductCategoryInformation(Long id, String name) {
    public static ProductCategoryInformation of(Long id, String name) {
        return new ProductCategoryInformation(id, name);
    }
}
