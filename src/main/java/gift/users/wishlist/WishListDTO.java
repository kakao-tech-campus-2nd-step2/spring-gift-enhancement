package gift.users.wishlist;

public class WishListDTO {

    private long userId;
    private long productId;
    private int num;
    private long optionId;

    public WishListDTO() {
    }

    public WishListDTO(long userId, long productId, int num, long optionId) {
        this.userId = userId;
        this.productId = productId;
        this.num = num;
        this.optionId = optionId;
    }

    public long getProductId() {
        return productId;
    }

    public long getOptionId() {
        return optionId;
    }

    public int getNum() {
        return num;
    }

    public static WishListDTO fromWishList(WishList wishList) {
        return new WishListDTO(wishList.getUser().getId(), wishList.getProduct().getId(),
            wishList.getNum(), wishList.getOption().getId());
    }
}
