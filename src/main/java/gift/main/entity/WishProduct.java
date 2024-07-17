package gift.main.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "wish-products")
public class WishProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    public Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;


    public WishProduct() {

    }

    public WishProduct(Product product, User user) {
        this.product = product;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }

    public void setProductIdToNull() {
        this.product = null;
    }

}
