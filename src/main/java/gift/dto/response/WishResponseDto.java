package gift.dto.response;

import gift.domain.Wish;

public record WishResponseDto(Long id, ProductResponseDto productResponseDto, int quantity) {
    public static WishResponseDto from(final Wish wish){
        return new WishResponseDto(wish.getId(), ProductResponseDto.from(wish.getProduct()), wish.getQuantity());
    }
}
