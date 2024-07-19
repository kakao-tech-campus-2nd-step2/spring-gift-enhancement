package gift.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "product_id"})
})
public class Option extends BasicEntity{
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    protected Option() {}

    public Option(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Option(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, int quantity, Product product) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public Option(String name, int quantity, Product product) {
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public void updateOption(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isSameName(String theirName) {
        return name.equals(theirName);
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
}
