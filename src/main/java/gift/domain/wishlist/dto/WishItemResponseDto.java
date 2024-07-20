package gift.domain.wishlist.dto;

import gift.domain.product.dto.ProductReadAllResponse;
import gift.domain.user.dto.UserDto;
import gift.domain.wishlist.entity.WishItem;

public record WishItemResponseDto(
    Long id,
    UserDto user,
    ProductReadAllResponse product
) {
    public static WishItemResponseDto from(WishItem wishItem) {
        return new WishItemResponseDto(
            wishItem.getId(),
            UserDto.from(wishItem.getUser()),
            ProductReadAllResponse.from(wishItem.getProduct())
        );
    }
}
