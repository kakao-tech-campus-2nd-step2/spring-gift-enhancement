package gift.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="option", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "product_id"})})
public class Option {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = false, nullable = false)
    @Size(max = 50, message = "Option name must be 50 characters or less.")
    @Pattern(regexp = "^[\\w\\s\\[\\]\\(\\)\\+\\-\\&\\/]*$", message = "Option name contains invalid characters.")
    private String name;

    @Column(name="quantity")
    @Min(value = 1, message = "Quantity must be at least 1.")
    @Max(value = 100000000, message = "Quantity must be less than 100,000,000.")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Option() {

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

    public Product getProduct() {
        return product;
    }

    // Add setters if needed
}
