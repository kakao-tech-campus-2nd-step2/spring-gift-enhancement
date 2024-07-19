package gift.option.model;

import gift.product.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "product_options", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_id","option_name"})
}) // 상품 id와 옵션 종류(이름)이 유일해야함 -> 옵션 이름 중복 불가능
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Embedded
    private OptionName optionName;

    @Min(value = 1, message = "옵션 수량은 최소 1개 이상이어야 합니다.")
    @Max(value = 99999999, message = "옵션 수량은 1억 개 미만이어야 합니다.")
    @Column(nullable = false)
    private int quantity;

    // JPA에서 필요로 하는 기본 생성자
    protected Option() {
    }

    public Option(Product product, OptionName optionName, int quantity) {
        this.product = product;
        this.optionName = optionName;
        this.quantity = quantity;
    }

    public Long getOptionId() {
        return optionId;
    }

    public Product getProduct() {
        return product;
    }

    public OptionName getOptionName() {
        return optionName;
    }

    public int getQuantity() {
        return quantity;
    }

}