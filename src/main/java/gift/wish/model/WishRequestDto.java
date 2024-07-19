package gift.wish.model;

public record WishRequestDto(Long productId, Integer count) {

    public boolean isCountZero() {
        return count == 0;
    }
}
