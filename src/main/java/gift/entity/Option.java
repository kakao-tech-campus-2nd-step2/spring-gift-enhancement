package gift.entity;

import jakarta.persistence.*;

@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    protected Option() {
    }

    public Option(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void associateWithProduct(Product product) {
        this.product = product;
    }

    public void subtract(int quantity) {
        this.quantity -= quantity;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
