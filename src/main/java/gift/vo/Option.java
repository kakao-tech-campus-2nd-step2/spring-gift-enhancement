package gift.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore //@Todo 추후 삭제 필요
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_option_product_id_ref_product_id"))
    private Product product;

    @NotNull
    private String name;

    @NotNull
    private int quantity;

    public Option(Long id, Product product, String name, int quantity) {
        validateName(name);
        this.id = id;
        this.product = product;
        this.name = name;
        this.quantity = quantity;
    }

    public Option() {

    }

    private static void validateName(String name) {
        if (name == null || name.length() > 50) {
            throw new IllegalArgumentException("옵션명은 공백 포함하여 최대 50자까지 가능합니다.");
        }
        if (!name.matches("^[a-zA-Z0-9가-힣 ()\\[\\]+\\-&/]*$")) {
            throw new IllegalArgumentException("상품명에 () [] + - & / 외의 특수기호는 불가합니다");
        }
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

    public int getQuantity() {
        return quantity;
    }
}
