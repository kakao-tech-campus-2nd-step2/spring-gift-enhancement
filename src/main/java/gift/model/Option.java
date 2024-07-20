package gift.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "options")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "옵션 이름은 필수입니다")
    @Size(max = 50, message = "옵션 이름은 최대 50자까지 입력 가능합니다")
    @Pattern(regexp = "^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣()\\[\\]+\\-&/_.\\s]*$", message = "사용 불가능한 특수 문자가 포함되어 있습니다")
    @Column(nullable = false)
    private String name;

    @Min(value = 1, message = "수량은 최소 1개 이상이어야 합니다")
    @Max(value = 999999999, message = "수량은 1억개 미만이어야 합니다")
    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    protected Option() {
    }

    public Option(String name, int quantity, Product product) {
        this.name = name;
        this.quantity = quantity;
        this.product = product;
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
}