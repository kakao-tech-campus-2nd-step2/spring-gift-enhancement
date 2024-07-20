package gift.product.domain;

import jakarta.persistence.*;

@Entity(name = "product_option")
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private Long quentity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductOption(String name, Long quentity, Product product) {
        this.name = name;
        this.quentity = quentity;
        product.addProductOption(this);
    }

    public ProductOption() {
    }

    public String getName() {
        return name;
    }
}
