package gift.wishList;

public class WishListDTO {
    long optionID;

    long count;

    public WishListDTO() {
    }

    public WishListDTO(long optionID, long count) {
        this.optionID = optionID;
        this.count = count;
    }

    public WishListDTO(WishList wishList) {
        this.optionID = wishList.getOption().getId();
        this.count = wishList.getCount();
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getOptionID() {
        return optionID;
    }

    public void setOptionID(long productID) {
        this.optionID = productID;
    }

}
