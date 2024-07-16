package gift.dto.wish.response;

import gift.entity.Product;

public record WishResponse(
    Long id,
    Product product,
    Integer quantity
) {

}
