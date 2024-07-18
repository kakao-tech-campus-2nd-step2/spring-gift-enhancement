package gift.model.product;

import jakarta.persistence.*;

@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(length = 50)
    private String name;

    @Column(nullable = false)
    private  int amount;

    protected Option(){
    }

    public Option(Product product, String name, int amount){
        this.product = product;
        this.name = name;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }
}
