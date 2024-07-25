package gift.wishlist.dto;

import gift.product.dto.ProductResponseDto;
import gift.wishlist.entity.WishList;

// 위시리스트를 조작해서 나온 결과를 담는 dto
public record WishListResponseDto(
    long wishListId,
    long userId,
    ProductResponseDto product) {

    public static WishListResponseDto fromWishList(WishList wishList) {
        return new WishListResponseDto(wishList.getWishListId(),
            wishList.getUserProduct().getUserId(),
            ProductResponseDto.fromProduct(wishList.getUserProduct().getProduct()));
    }
}
