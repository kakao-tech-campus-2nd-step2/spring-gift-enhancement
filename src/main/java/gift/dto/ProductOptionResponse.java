package gift.dto;

public record ProductOptionResponse(Long id, String name, Integer quantity) {
    public static ProductOptionResponse of(Long id, String name, Integer quantity) {
        return new ProductOptionResponse(id, name, quantity);
    }
}
