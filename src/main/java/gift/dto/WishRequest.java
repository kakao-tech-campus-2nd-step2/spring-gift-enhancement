package gift.dto;

public class WishRequest {
    private final Long productId;

    public WishRequest(Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("상품 id를 입력해야 합니다.");
        }
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}