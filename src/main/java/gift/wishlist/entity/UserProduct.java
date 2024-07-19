package gift.wishlist.entity;

import gift.product.entity.Product;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// userId와 product 조합은 unique해야 하므로 embeddable로 만들었습니다.
@Embeddable
public class UserProduct {
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    protected UserProduct() {
    }

    public UserProduct(Long userId, Product product) {
        this.userId = userId;
        this.product = product;
    }

    public Long getUserId() {
        return userId;
    }

    public Product getProduct() {
        return product;
    }
}
