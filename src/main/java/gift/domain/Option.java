package gift.domain;

import gift.response.OptionResponse;
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

    public OptionResponse toDto() {
        return new OptionResponse(this.id, this.name, this.quantity);
    }

}
