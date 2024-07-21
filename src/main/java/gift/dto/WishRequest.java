package gift.dto;

public class WishRequest {
    private final Long productId;

    public WishRequest(Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID는 null일 수 없습니다.");
        }
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}