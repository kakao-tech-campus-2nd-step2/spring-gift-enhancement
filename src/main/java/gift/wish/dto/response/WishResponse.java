package gift.wish.dto.response;


import gift.product.entity.Product;

public record WishResponse(
    Long id,
    Product product,
    Integer quantity
) {

}
