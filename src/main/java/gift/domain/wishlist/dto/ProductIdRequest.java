package gift.domain.wishlist.dto;

public class ProductIdRequest {

    private Long productId;

    private ProductIdRequest() {
    }

    public ProductIdRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
