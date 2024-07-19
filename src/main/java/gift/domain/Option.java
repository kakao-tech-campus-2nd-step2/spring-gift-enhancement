package gift.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Pattern;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "option", uniqueConstraints = {
    @UniqueConstraint(
        name = "NAME_PRODUCT_ID_UNIQUE",
        columnNames = {"name", "product_id"}
    )
})
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Option() {
    }

    public Option(String name, Long quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}