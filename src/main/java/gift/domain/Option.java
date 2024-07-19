package gift.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "상품 이름을 비우거나 공백으로 설정할 수 없습니다")
    @Size(max=50,message = "옵션명은 공백 포함하여 최대 50자까지 입력할 수 있습니다")
    @Pattern(regexp = "^[\\w\\s\\(\\)\\[\\]\\+\\-\\&\\/\\_가-힣]*$", message = "특수 문자는 ( ), [ ], +, -, &, /, _ 만 사용할 수 있습니다.")
    @Column(name = "name")
    private String name;

    @Min(value = 1, message = "옵션 수량은 최소 1 이상이어야 합니다.")
    @Max(value = 99999999, message = "옵션 수량은 최대 1억 미만이어야 합니다.")
    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    public Option() {
    }

    public Option(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
