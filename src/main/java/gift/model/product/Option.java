package gift.model.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(255)")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣\\s()\\[\\]+\\-&/_]*$")
    @Size(max = 50)
    private String name;

    @Column(nullable = false, columnDefinition = "integer")
    @Min(value = 1)
    @Max(value = 100000000 - 1)
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
