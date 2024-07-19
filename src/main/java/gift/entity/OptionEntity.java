package gift.entity;

import jakarta.persistence.*;

@Entity
public class OptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    public OptionEntity() {}

    public OptionEntity(String name, int quantity, ProductEntity product) {
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

    public ProductEntity getProduct() {
        return product;
    }

    public void update(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}