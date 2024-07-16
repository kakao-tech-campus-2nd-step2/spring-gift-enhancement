package gift.option;

import gift.product.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "OPTIONS")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "quantity")
    private Long quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public Option() {
    }

    public Option(OptionRequest optionRequest, Product product) {
        this.name = optionRequest.getName();
        this.quantity = optionRequest.getQuantity();
        this.product = product;
    }
    public Option(String name, Long quantity, Product product) {
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public void updateOption(OptionRequest optionRequest){
        this.name = optionRequest.getName();
        this.quantity = optionRequest.getQuantity();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
