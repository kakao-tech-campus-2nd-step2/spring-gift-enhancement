package gift.domain.exception.notFound;

public class ProductNotIncludedInWishlistException extends NotFoundException {

    public ProductNotIncludedInWishlistException() {
        super("The product is not included your wishlist.");
    }
}
