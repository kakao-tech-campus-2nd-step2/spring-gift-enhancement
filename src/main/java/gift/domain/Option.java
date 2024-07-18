package gift.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "option", uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "name"}))
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long option_id;

    @Column(nullable = false, length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Option(String name, Product product) {
        this.name = name;
        this.product = product;
    }

    public String getName() {
        return name;
    }
}
