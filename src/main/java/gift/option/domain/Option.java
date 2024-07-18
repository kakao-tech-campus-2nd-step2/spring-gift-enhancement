package gift.option.domain;

import gift.category.domain.Category;
import gift.product.domain.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name="options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50, message = "이름은 최대 50글자입니다.")
    @Pattern(regexp = "^[A-Za-z0-9 ()\\[\\]+\\-&/_ㄱ-ㅣ가-힣]+$", message = "특수문자는 (),[],+,-,&,/,_만 허용됩니다.")
    @UniqueElements
    private String name;

    @NotNull
    @Column(columnDefinition = "bigint default 0")
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Constructor
    public Option() {
    }

    public Option(String name, Long quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Option(Long id, String name, Long quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    // gette and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
