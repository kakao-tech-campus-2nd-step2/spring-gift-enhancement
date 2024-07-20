package gift.option.model;

import gift.common.exception.OptionException;
import gift.option.OptionErrorCode;
import gift.product.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    protected Option() {
    }

    public Option(String name, Integer quantity, Product product) {
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

    public Integer getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void validateDuplicated(Option other) throws OptionException {
        if (name.equals(other.getName())) {
            throw new OptionException(OptionErrorCode.NAME_DUPLICATED);
        }
    }

    public void updateInfo(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
