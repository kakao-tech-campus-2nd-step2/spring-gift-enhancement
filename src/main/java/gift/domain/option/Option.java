package gift.domain.option;

import gift.domain.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    protected Option() {
    }

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

    public boolean hasSameName(Option target) {
        return this.getName().equals(target.getName());
    }
}
