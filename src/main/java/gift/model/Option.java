package gift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Entity
@Table(name = "options", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "product_id"}))
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT COMMENT '옵션 ID'")
    private Long id;

    @Embedded
    @Valid
    private OptionName name;

    @Embedded
    @Valid
    private OptionQuantity quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    protected Option() {}

    public Option(Long id, OptionName name, OptionQuantity quantity, Product product) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

        public Option(Long id,  OptionName name, OptionQuantity quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public OptionName getName() {
        return name;
    }

    public OptionQuantity getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    // Option management methods
    public void assignProduct(Product product) {
        this.product = product;
    }

    public void removeProduct() {
        this.product = null;
    }

    public void update(OptionName name, OptionQuantity quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public void decreaseQuantity(int amount) {
        this.quantity = new OptionQuantity(this.quantity.getQuantity() - amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return Objects.equals(id, option.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}