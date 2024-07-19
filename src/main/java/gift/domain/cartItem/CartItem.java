package gift.domain.cartItem;

import gift.domain.product.Product;
import gift.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int count = 1;

    protected CartItem() {
    }

    public CartItem(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "CartItem{" +
               "id=" + id +
               ", user=" + user +
               ", product=" + product +
               '}';
    }

    public int addOneMore() {
        this.count += 1;
        return count;
    }

    public void updateCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItem cartItem = (CartItem) o;

//        // 프록시 객체 초기화
//        Hibernate.initialize(cartItem.getUser());
//        Hibernate.initialize(user);

        return count == cartItem.count &&
               Objects.equals(id, cartItem.id) &&
               Objects.equals(user, cartItem.user) &&
               Objects.equals(product.getId(), cartItem.product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, product, count);
    }
}
