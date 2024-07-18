package gift.core.domain.product;

public record ProductOption(
        Long id,
        String name,
        Integer quantity
) {
    public static ProductOption of(String name, Integer quantity) {
        return new ProductOption(0L, name, quantity);
    }
}
