package gift.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "옵션 이름은 반드시 입력해야 합니다.")
    @Size(max = 50, message = "옵션 이름은 공백을 포함하여 최대 50자까지 입력할 수 있습니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9\\s\\(\\)\\[\\]+\\-&/_]*$", message = "특수문자는 '(), [], +, -, &, /, _'만 사용할 수 있습니다.")
    @Column(name = "name", nullable = false)
    private String name;

    @Min(value = 1, message = "옵션 수량은 최소 1개 이상이어야 합니다.")
    @Max(value = 99999999, message = "옵션 수량은 1억 개 미만이어야 합니다.")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Option() {

    }

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
