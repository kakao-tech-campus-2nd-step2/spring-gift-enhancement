package gift.product.option.entity;

import gift.product.entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "options")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_options_product_id_ref_products_id"))
    private Product product;

    public Option(String name, Integer quantity, Product product) {
        if (quantity < 0 || quantity >= 100_000_000) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    protected Option() {
    }

    public String getName() {
        return name;
    }

}
