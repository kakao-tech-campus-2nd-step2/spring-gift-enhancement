package gift.wish.model;

public class WishRequestDto {

    private Long productId;
    private Integer count;

    public WishRequestDto() {
    }

    public WishRequestDto(Long productId, Integer count) {
        this.productId = productId;
        this.count = count;
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
