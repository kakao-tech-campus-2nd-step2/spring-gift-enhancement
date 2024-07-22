package gift.wishlist.dto;

import gift.product.entity.Product;

// 위시리스트에 넣는 요청 시 사용할 dto.
public record WishListRequestDto(
    Product product
) {

}
