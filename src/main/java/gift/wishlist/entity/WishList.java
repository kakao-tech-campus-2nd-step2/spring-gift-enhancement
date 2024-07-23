package gift.wishlist.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

// wishlist 객체. quantity는 없앴습니다.
@Entity
@Table(name = "wish_products", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "product_id"})})
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishListId;

    @Embedded
    private UserProduct userProduct;

    protected WishList() {
    }

    public WishList(Long wishListId, UserProduct userProduct) {
        this.wishListId = wishListId;
        this.userProduct = userProduct;
    }

    public WishList(UserProduct userProduct) {
        this(null, userProduct);
    }

    public Long getWishListId() {
        return wishListId;
    }

    public UserProduct getUserProduct() {
        return userProduct;
    }
}
