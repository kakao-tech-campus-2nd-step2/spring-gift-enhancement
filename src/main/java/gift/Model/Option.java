package gift.Model;

import jakarta.persistence.*;

@Entity
public class Option {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Product product;

    private Option() {}

    public Option(String name, int quantity, Product product) {
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void update(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }

    public boolean hasSameName(String name){
        return this.name.equals(name);
    }

    public boolean isBelongToProduct(Long productId){
        return this.product.getId().equals(productId);
    }
}
