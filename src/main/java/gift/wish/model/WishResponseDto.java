package gift.wish.model;

public record WishResponseDto(Long productId, Integer count) {

    public static WishResponseDto from(Wish wish) {
        return new WishResponseDto(
            wish.getProduct().getId(),
            wish.getCount()
        );
    }
}
