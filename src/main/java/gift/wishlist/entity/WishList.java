package gift.wishlist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "wish_products", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "product_id"})})
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishListId;

    @Embedded
    private UserProduct userProduct;

    @Column(nullable = false)
    private int quantity;

    protected WishList() {
    }

    public WishList(UserProduct userProduct, int quantity) {
        this.wishListId = null;
        this.userProduct = userProduct;
        this.quantity = quantity;
    }

    public Long getWishListId() {
        return wishListId;
    }

    public UserProduct getUserProduct() {
        return userProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }
}
