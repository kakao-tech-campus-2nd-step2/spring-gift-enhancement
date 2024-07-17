package gift.util.mapper;

import gift.product.entity.Product;
import gift.wish.dto.response.WishResponse;
import gift.wish.entity.Wish;

public class WishMapper {

    public static WishResponse toResponse(Wish wish) {
        Product product = Product.builder()
            .id(wish.getProduct().getId())
            .name(wish.getProduct().getName())
            .price(wish.getProduct().getPrice())
            .imageUrl(wish.getProduct().getImageUrl())
            .build();
        return new WishResponse(wish.getId(), product, wish.getQuantity());
    }

}
