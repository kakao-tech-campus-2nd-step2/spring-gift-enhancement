package gift.domain;

import gift.dto.OptionDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id", "name"})})
@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(nullable = false, length = 50)
    private String name;

    @Min(1)
    @Max(99999999)
    @Column(nullable = false)
    private Long quantity;

    protected Option() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Option(String name, Long quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public OptionDto toDto() {
        return new OptionDto(this.id, this.name, this.quantity);
    }

    public void setId(Long id) {
        this.id = id;
    }

}
