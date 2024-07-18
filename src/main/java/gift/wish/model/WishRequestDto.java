package gift.wish.model;

public class WishRequestDto {

    private Long productId;
    private Integer count;

    public WishRequestDto() {
    }

    public WishRequestDto(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getCount() {
        return count;
    }

    public boolean isCountZero() {
        return count == 0;
    }
}
