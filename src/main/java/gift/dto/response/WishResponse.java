package gift.dto.response;

import gift.domain.Wish;

public record WishResponse(Long id, ProductResponse productResponseDto, int quantity) {
    public static WishResponse from(final Wish wish){
        return new WishResponse(wish.getId(), ProductResponse.from(wish.getProduct()), wish.getQuantity());
    }
}
